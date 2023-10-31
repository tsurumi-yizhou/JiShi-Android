@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.jishi.Result
import me.yihtseu.jishi.ui.component.Loading
import me.yihtseu.jishi.vm.ClassroomViewModel

@Composable
fun ClassroomScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ClassroomViewModel = hiltViewModel()
) {
    val host = remember { SnackbarHostState() }
    val status = rememberBottomSheetScaffoldState()

    val buildings by viewModel.buildings.collectAsState()
    val classrooms by viewModel.classrooms.collectAsState()

    if (buildings is Result.Error) {
        (buildings as Result.Error).message?.let {
            LaunchedEffect(it) {
                host.showSnackbar(it)
            }
        }
    }

    if (classrooms is Result.Error) {
        (classrooms as Result.Error).message?.let {
            LaunchedEffect(it) {
                host.showSnackbar(it)
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = status,
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
        sheetContent = {
            when(buildings) {
                is Result.Success -> {
                    (buildings as Result.Success).data.forEach {
                        FilterChip(
                            false, {}, label = {
                                Text(text = it.name)
                            }
                        )
                    }
                }
                else -> {}
            }
        },
        snackbarHost = {
            SnackbarHost(host)
        }
    ) { paddingValues ->
        when(classrooms) {
            is Result.Success -> {
                LazyColumn(
                    modifier = modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    items((classrooms as Result.Success).data) {
                        Text(text = it.toString())
                    }
                }
            }
            else -> Loading(modifier = Modifier.padding(paddingValues))
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.init()
    }
}