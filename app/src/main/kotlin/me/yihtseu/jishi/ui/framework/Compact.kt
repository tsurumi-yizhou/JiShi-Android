@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.framework

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import me.yihtseu.jishi.ui.component.box.LoadingBox
import me.yihtseu.jishi.ui.theme.typography

@Composable
fun Compact(
    title: String,
    controller: NavHostController? = null,
    actions: Map<ImageVector, () -> Unit> = emptyMap(),
    bottom: @Composable () -> Unit = {},
    drawer: @Composable (ColumnScope.() -> Unit)? = null,
    message: String?,
    loading: Boolean,
    content: LazyListScope.() -> Unit
) {
    val host = remember { SnackbarHostState() }
    val show = remember { mutableStateOf(true) }
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                scrollBehavior = behavior,
                title = {
                    Text(text = title, style = typography.titleMedium)
                },
                navigationIcon = {
                    controller?.let {
                        IconButton(
                            onClick = {
                                controller.popBackStack()
                            }
                        ) {
                            Icon(Icons.Outlined.ArrowBack, null)
                        }
                    }
                },
                actions = {
                    actions.forEach { (icon, action) ->
                        IconButton(
                            onClick = action
                        ) {
                            Icon(icon, null)
                        }
                    }
                }
            )
        },
        bottomBar = bottom,
        floatingActionButton = {
            drawer?.let {
                IconButton(
                    onClick = {
                        show.value = !show.value
                    }
                ) {
                    Icon(Icons.Outlined.ExpandLess, null)
                }
            }
        },
        snackbarHost = {
            SnackbarHost(host)
        }
    ) { paddingValues ->
        if (loading) {
            LoadingBox(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.nestedScroll(behavior.nestedScrollConnection),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                content()
                item {
                    Spacer(modifier = Modifier)
                }
            }
        }
        if (drawer != null && show.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    show.value = false
                },
                content = drawer
            )
        }
    }

    LaunchedEffect(message) {
        message?.let {
            host.showSnackbar(it)
        }
    }
}