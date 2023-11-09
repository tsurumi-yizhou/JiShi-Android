@file:OptIn(ExperimentalPermissionsApi::class)

package me.yihtseu.jishi.ui.page

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.Navigation
import me.yihtseu.jishi.ui.component.card.NewsCard
import me.yihtseu.jishi.ui.framework.BottomBar
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.ui.theme.typography
import me.yihtseu.jishi.vm.NewsViewModel

@Composable
fun NewsScreen(
    controller: NavHostController,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    val entries = state.entries?.collectAsLazyPagingItems()

    val context = LocalContext.current
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
        if (state.feeds.isNotEmpty()) item {
            ScrollableTabRow(
                selectedTabIndex = selectedIndex.intValue,
                modifier = Modifier.fillMaxWidth()
            ) {
                state.feeds.forEachIndexed { index, feed ->
                    Tab(
                        selected = selectedIndex.intValue == index,
                        onClick = {
                            if (selectedIndex.intValue != index) {
                                viewModel.load(feed)
                                selectedIndex.intValue = index
                            }
                        },
                        text = {
                            Text(text = feed.title, style = typography.labelSmall)
                        }
                    )
                }
            }
        }

        entries?.let { entries ->
            items(entries.itemCount) {
                entries[it]?.let { entry ->
                    NewsCard(
                        title = entry.title,
                        time = entry.updated,
                        desc = entry.abstract,
                        image = entry.image
                    ) {
                        val intent = Intent().apply {
                            action = Intent.ACTION_VIEW
                            data = Uri.parse(entry.link)
                        }
                        context.startActivity(intent)
                    }
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