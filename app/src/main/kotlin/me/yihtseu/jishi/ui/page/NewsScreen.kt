@file:OptIn(ExperimentalPermissionsApi::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.Navigation
import me.yihtseu.jishi.ui.framework.BottomBar
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.vm.NewsViewModel

@Composable
fun NewsScreen(
    controller: NavHostController,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val entries = state.entries?.collectAsLazyPagingItems()

    val notifyPermission =
        rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)

    Compact(
        title = stringResource(R.string.news),
        loading = state.loading,
        message = state.message,
        bottom = {
            BottomBar(Navigation.NewsScreen.id.toString(), controller)
        }
    ) {
        entries?.let { entries ->
            items(entries.itemCount) {
                entries[it]?.let {
                }
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.init()
    }

    LaunchedEffect(notifyPermission) {
        if (!notifyPermission.status.isGranted) {
            notifyPermission.launchPermissionRequest()
        }
    }
}