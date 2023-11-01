@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
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
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.ui.component.box.LoadingBox
import me.yihtseu.jishi.ui.component.chip.ScoreChip
import me.yihtseu.jishi.ui.theme.shapes
import me.yihtseu.jishi.vm.ScoreViewModel

@Composable
fun ScoreScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ScoreViewModel = hiltViewModel()
) {
    val gpa by viewModel.gpa.collectAsState()
    val selected by viewModel.selected.collectAsState()
    val subjects by viewModel.subjects.collectAsState()
    val host = remember { SnackbarHostState() }
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.score))
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
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            if (subjects is State.Success) (subjects as State.Success).let {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    items(it.data) { subject ->
                        ScoreChip(
                            name = subject.name,
                            type = subject.type,
                            checked = selected.contains(subject.name)
                        ) {
                            viewModel.select(it, subject.name)
                        }
                    }
                }
            } else LoadingBox(modifier = Modifier.fillMaxSize())
        }

        if (showBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet.value = false
                },
                sheetState = sheetState
            ) {
            }
        }
    }

    if (subjects is State.Error) (subjects as State.Error).let {
        LaunchedEffect(it.message) {
            it.message?.let {
                host.showSnackbar(it)
            }
        }
    }

    LaunchedEffect(gpa) {
        if (gpa != 0.0) {
            host.showSnackbar(context.getString(R.string.score_result).format(gpa))
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.load()
    }
}