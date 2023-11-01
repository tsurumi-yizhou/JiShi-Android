@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.campus.edu.Building
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.ui.component.box.LoadingBox
import me.yihtseu.jishi.ui.component.box.QueryDrawer
import me.yihtseu.jishi.ui.component.card.RoomCard
import me.yihtseu.jishi.ui.theme.shapes
import me.yihtseu.jishi.utils.system.share
import me.yihtseu.jishi.vm.ClassroomViewModel

@Composable
fun ClassroomScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ClassroomViewModel = hiltViewModel()
) {
    val host = remember { SnackbarHostState() }
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(true) }

    val buildings by viewModel.buildings.collectAsState()
    val classrooms by viewModel.classrooms.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.classroom))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            controller.popBackStack()
                        }
                    ) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(host)
        },
        floatingActionButton = {
            Button(
                shape = shapes.extraSmall,
                modifier = Modifier.size(60.dp),
                onClick = {
                    showBottomSheet.value = true
                }
            ) {
                Icon(Icons.Outlined.Add, null)
            }
        }
    ) { paddingValues ->
        when (classrooms) {
            is State.Success -> (classrooms as State.Success).let {
                LazyColumn(
                    modifier = modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    items(it.data) {
                        RoomCard(
                            building = it.building,
                            name = it.name,
                            duration = it.duration,
                            share = { share(context, it) },
                            onClick = {}
                        )
                    }
                }
            }

            else -> LoadingBox(modifier = Modifier.padding(paddingValues))
        }
        if (showBottomSheet.value && buildings is State.Success) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet.value = false
                },
                sheetState = sheetState
            ) {
                QueryDrawer(
                    modifier = Modifier.fillMaxWidth(),
                    buildings = (buildings as State.Success).data.map {
                        Building(
                            zone = it.otherFields.zoneCode.toInt(),
                            id = it.id,
                            name = it.name
                        )
                    }
                ) { zone, code, date, start, end ->
                    viewModel.query(zone, code, date, start, end)
                    showBottomSheet.value = false
                }
            }
        }
    }

    if (buildings is State.Error) {
        (buildings as State.Error).message?.let {
            LaunchedEffect(it) {
                host.showSnackbar(it)
            }
        }
    }

    if (classrooms is State.Error) {
        (classrooms as State.Error).message?.let {
            LaunchedEffect(it) {
                host.showSnackbar(it)
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.init()
    }
}