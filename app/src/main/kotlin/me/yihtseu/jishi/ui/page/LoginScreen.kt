package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.ui.component.box.LoadingBox
import me.yihtseu.jishi.ui.component.box.LoginBox
import me.yihtseu.jishi.ui.theme.HorizontalCardPadding
import me.yihtseu.jishi.ui.theme.VerticalCardPadding
import me.yihtseu.jishi.vm.LoginViewModel

@Composable
fun LoginScreen(
    controller: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val host = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(host)
        }
    ) { paddingValues ->
        if (state.loading) {
            LoadingBox(modifier = Modifier.padding(paddingValues).fillMaxSize())
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                LoginBox(
                    account = state.username,
                    passwd = state.password,
                    modifier = Modifier
                        .padding(HorizontalCardPadding, VerticalCardPadding),
                    onLogin = { username, password ->
                        viewModel.login(username, password)
                    }
                )
            }
        }
    }

    LaunchedEffect(state.message) {
        state.message?.let {
            host.showSnackbar(it)
        }
    }

    LaunchedEffect(state.success) {
        if (state.success) {
            controller.navigate("home") {
                popUpTo(0)
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.preLogin()
        if (state.username != null && state.password != null) {
            viewModel.login(state.username!!, state.password!!)
        }
    }
}