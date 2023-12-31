@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.framework

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
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
    action: Uri? = null,
    loading: Boolean,
    content: @Composable (NestedScrollConnection) -> Unit
) {
    val context = LocalContext.current
    val host = remember { SnackbarHostState() }
    val show = remember { mutableStateOf(true) }
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                scrollBehavior = behavior,
                title = {
                    Text(text = title, style = typography.titleLarge)
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
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                content(behavior.nestedScrollConnection)
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
        message?.let { message ->
            action?.let {
                val result = host.showSnackbar(
                    message = message,
                    actionLabel = context.getString(R.string.download),
                    duration = SnackbarDuration.Short
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        val intent = Intent(Intent.ACTION_VIEW, it)
                        context.startActivity(Intent.createChooser(intent, null))
                    }

                    SnackbarResult.Dismissed -> {
                    }
                }
            } ?: let {
                host.showSnackbar(message)
            }
        }
    }
}