@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.MainActivity
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.box.LoginBox
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.ui.theme.HorizontalCardPadding
import me.yihtseu.jishi.ui.theme.VerticalCardPadding
import me.yihtseu.jishi.ui.theme.shapes
import me.yihtseu.jishi.vm.CampusCardViewModel

@Composable
fun CampusCardScreen(
    controller: NavHostController,
    viewModel: CampusCardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    Compact(
        title = stringResource(R.string.campus_card_account),
        controller = controller,
        loading = state.loading,
        message = state.message
    ) {
        if (state.login) {
            LazyColumn(modifier = Modifier.nestedScroll(it)) {
                item {
                    ElevatedCard(
                        modifier = Modifier.padding(HorizontalCardPadding, VerticalCardPadding).fillMaxWidth(),
                        shape = shapes.small
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            OutlinedButton(
                                modifier = Modifier
                                    .padding(HorizontalCardPadding, VerticalCardPadding)
                                    .fillMaxWidth(),
                                onClick = {
                                    viewModel.exit()
                                    (context as MainActivity).finish()
                                }
                            ) {
                                Text(text = stringResource(R.string.exit))
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(modifier = Modifier.nestedScroll(it)) {
                item {
                    LoginBox(
                        state.username, state.password,
                        modifier = Modifier.padding(HorizontalCardPadding, VerticalCardPadding)
                    ) { username, password ->
                        viewModel.login(username, password)
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.init()
    }
}