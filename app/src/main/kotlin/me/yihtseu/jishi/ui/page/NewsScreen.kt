@file:OptIn(ExperimentalPermissionsApi::class)

package me.yihtseu.jishi.ui.page

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.jishi.PageState
import me.yihtseu.jishi.ui.Navigation
import me.yihtseu.jishi.ui.component.card.NewsCard
import me.yihtseu.jishi.ui.framework.BottomBar
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.ui.theme.typography
import me.yihtseu.jishi.vm.NewsViewModel
import org.jsoup.Jsoup

@Composable
fun NewsScreen(
    controller: NavHostController,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    val entries = state.entries?.collectAsLazyPagingItems()

    Compact(
        title = stringResource(R.string.news),
        loading = state.loading,
        message = state.message,
        bottom = {
            BottomBar(Navigation.NewsScreen.id.toString(), controller)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.feeds.isNotEmpty()) {
                ScrollableTabRow(
                    selectedTabIndex = selectedIndex.intValue,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    state.feeds.forEachIndexed { index, feed ->
                        Tab(
                            selected = selectedIndex.intValue == index,
                            onClick = {
                                if (selectedIndex.intValue != index) {
                                    viewModel.load(index)
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

            LazyColumn(
                state = listState,
                modifier = Modifier.nestedScroll(it).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                entries?.let { entries ->
                    items(entries.itemCount) {
                        entries[it]?.let { entry ->
                            NewsCard(
                                title = entry.title,
                                time = entry.updated,
                                desc = Jsoup.parse(entry.abstract).text(),
                                image = entry.image
                            ) {
                                PageState.title = entry.title
                                PageState.content = entry.content
                                PageState.url = entry.link
                                controller.navigate("detail")
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.init()
        viewModel.load(selectedIndex.intValue)
    }

    val notifyPermission = if (Build.VERSION.SDK_INT > 33) {
        rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
    } else null

    LaunchedEffect(notifyPermission) {
        notifyPermission?.let {
            if (!it.status.isGranted) {
                it.launchPermissionRequest()
            }
        }
    }
}