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
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.ui.Navigation
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
    val status by viewModel.state.collectAsState()
    val host = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(host)
        }
    ) { paddingValues ->
        when (status) {
            is State.Error -> (status as State.Error).let {
                Column(
                    modifier = Modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    LoginBox(
                        modifier = Modifier.padding(
                            top = VerticalCardPadding,
                            start = HorizontalCardPadding,
                            end = HorizontalCardPadding
                        ),
                        onLogin = { username, password ->
                            viewModel.login(username, password)
                        }
                    )
                }
                LaunchedEffect(it.message) {
                    it.message?.let {
                        host.showSnackbar(it)
                    }
                }
            }

            State.Loading -> LoadingBox(modifier = Modifier.padding(paddingValues).fillMaxSize())
            is State.Success -> (status as State.Success).let {
                LoadingBox(modifier = Modifier.padding(paddingValues).fillMaxSize())
                LaunchedEffect(it.data) {
                    controller.navigate(Navigation.MainScreen.id.toString()) {
                        popUpTo(0)
                    }
                }
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.preLogin()
    }
}