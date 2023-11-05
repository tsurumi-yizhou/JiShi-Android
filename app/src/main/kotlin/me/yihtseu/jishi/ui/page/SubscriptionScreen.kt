@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.vm.SubscriptionViewModel

@Composable
fun SubscriptionScreen(
    controller: NavHostController,
    viewModel: SubscriptionViewModel = hiltViewModel()
) {
    val message = remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    Compact(
        title = stringResource(R.string.theme_subscription),
        controller = controller,
        message = message.value,
        loading = false
    ) {
    }
}