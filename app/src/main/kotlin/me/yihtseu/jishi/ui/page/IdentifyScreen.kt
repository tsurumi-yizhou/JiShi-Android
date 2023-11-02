@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.ui.component.box.LoadingBox
import me.yihtseu.jishi.vm.IdentifyViewModel

@Composable
fun IdentifyScreen(
    controller: NavHostController,
    viewModel: IdentifyViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val host = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.qrcode))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { controller.popBackStack() }
                    ) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(host)
        }
    ) { paddingValue ->

        when (state) {
            is State.Success -> {
                val data = (state as State.Success).data
                Column(
                    modifier = Modifier.padding(paddingValue).fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        bitmap = BitmapFactory.decodeByteArray(data, 0, data.size).asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(300.dp, 300.dp)
                    )
                    IconButton(
                        onClick = { viewModel.load(host) }
                    ) {
                        Icon(Icons.Outlined.Refresh, null)
                    }
                }
            }
            State.Loading -> LoadingBox(modifier = Modifier.padding(paddingValue).fillMaxSize())
            is State.Error -> {
                LoadingBox(modifier = Modifier.padding(paddingValue).fillMaxSize())
                LaunchedEffect((state as State.Error).message) {
                    (state as State.Error).message?.let {
                        host.showSnackbar(it)
                    }
                }
            }
        }
    }

    LaunchedEffect(controller) {
        viewModel.load(host)
    }
}