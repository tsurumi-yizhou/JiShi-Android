package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.jishi.Result
import me.yihtseu.jishi.ui.component.Loading
import me.yihtseu.jishi.ui.component.LoginBox
import me.yihtseu.jishi.ui.theme.HorizontalCardPadding
import me.yihtseu.jishi.ui.theme.VerticalCardPadding
import me.yihtseu.jishi.vm.LoginViewModel

@Composable
fun LoginScreen(
    controller: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val status by viewModel.state.collectAsState()
    val context = LocalContext.current

    Scaffold { paddingValues ->
        when (status) {
            is Result.Error -> {
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
            }

            Result.Loading -> Loading(modifier = Modifier.padding(paddingValues).fillMaxSize())
            is Result.Success -> controller.navigate(R.string.main.toString()) {
                popUpTo(0)
            }
        }
    }

    LaunchedEffect(controller) {
        viewModel.preLogin(context)
    }
}

@Preview
@Composable
fun LoginPreview() {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LoginBox(
                modifier = Modifier.padding(top = VerticalCardPadding, start = HorizontalCardPadding, end = HorizontalCardPadding),
                onLogin = { username, password ->
                }
            )
        }
    }
}