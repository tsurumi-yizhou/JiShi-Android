@file:OptIn(ExperimentalPermissionsApi::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import me.yihtseu.jishi.ui.component.box.LoadingBox
import me.yihtseu.jishi.vm.NewsViewModel

@Composable
fun NewsScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val notifyPermission = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)

    LoadingBox(modifier = Modifier.fillMaxSize())

    LaunchedEffect(viewModel) {
        if (!notifyPermission.status.isGranted) {
            notifyPermission.launchPermissionRequest()
        }
    }
}