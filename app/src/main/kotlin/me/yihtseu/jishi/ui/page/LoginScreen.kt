package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.box.LoginBox
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.ui.theme.HorizontalCardPadding
import me.yihtseu.jishi.ui.theme.VerticalCardPadding
import me.yihtseu.jishi.vm.LoginViewModel

@Composable
fun LoginScreen(
    controller: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Compact(
        title = stringResource(R.string.app_name),
        loading = false,
        message = state.message
    ) {
        LazyColumn(
            modifier = Modifier
                .nestedScroll(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
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

    LaunchedEffect(state.success) {
        if (state.success) {
            controller.navigate("home") {
                popUpTo(0)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.preLogin()
    }
}