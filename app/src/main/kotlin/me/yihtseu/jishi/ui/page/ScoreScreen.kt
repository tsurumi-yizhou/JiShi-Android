@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
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
import me.yihtseu.jishi.vm.ScoreViewModel

@Composable
fun ScoreScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ScoreViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val host = remember { SnackbarHostState() }
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
        }
    ) { paddingValues ->
        when(state) {
            is Result.Error -> {
                val message = (state as Result.Error).message
                Loading(modifier = Modifier.padding(paddingValues))
                LaunchedEffect(message) {
                    message?.let {
                        host.showSnackbar(it)
                    }
                }
            }
            Result.Loading -> Loading(modifier = Modifier.padding(paddingValues))
            is Result.Success -> {
                val data = (state as Result.Success).data
                LazyColumn(
                    modifier = modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    items(data) {
                        Text(text = "${it.name} = ${it.score}")
                    }
                }
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.load()
    }
}