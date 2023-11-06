@file:OptIn(ExperimentalLayoutApi::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.InfoCard
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.vm.SubscriptionViewModel

@Composable
fun SubscriptionScreen(
    controller: NavHostController,
    viewModel: SubscriptionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    Compact(
        title = stringResource(R.string.theme_subscription),
        controller = controller,
        message = state.message,
        loading = state.loading
    ) {
        item {
            InfoCard(text = stringResource(R.string.internal_error))
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.load()
    }
}