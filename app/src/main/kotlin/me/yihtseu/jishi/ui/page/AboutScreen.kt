@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.BuildConfig
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.EntryCard
import me.yihtseu.jishi.ui.component.card.EntryItem
import me.yihtseu.jishi.ui.theme.*
import me.yihtseu.jishi.vm.AboutViewModel

@Composable
fun AboutScreen(
    controller: NavHostController,
    viewModel: AboutViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val host = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val contributors by viewModel.contributors.collectAsState()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(R.string.about_this_app))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            controller.popBackStack()
                        }
                    ) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val intent = Intent().apply {
                                action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                data = Uri.parse("package:${context.packageName}")
                            }
                            context.startActivity(intent)
                        }
                    ) {
                        Icon(Icons.Outlined.Info, null)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = {
            SnackbarHost(host)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            item {
                // Project Homepage
                OutlinedCard(
                    modifier = Modifier
                        .padding(HorizontalCardPadding, VerticalCardPadding)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.app_name), style = typography.titleLarge,
                            modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                        )
                        Text(
                            text = "${stringResource(R.string.version)}: ${BuildConfig.VERSION_NAME}",
                            style = typography.labelMedium,
                            modifier = Modifier.padding(HorizontalTextPadding, 0.dp)
                        )
                        Text(
                            text = stringResource(R.string.app_desc), style = typography.bodyMedium,
                            modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                        )
                    }
                }
            }

            item {
                if (contributors.isNotEmpty()) {
                    EntryCard(stringResource(R.string.contributors)) {
                        contributors.forEach {
                            EntryItem(Uri.parse(it.avatarUrl), it.login) {
                                val intent = Intent().apply {
                                    action = Intent.ACTION_VIEW
                                    data = Uri.parse(it.htmlUrl)
                                }
                                context.startActivity(intent)
                            }
                        }
                    }
                }
            }

            item {
                EntryCard(stringResource(R.string.contact)) {
                    EntryItem(Icons.Outlined.Check, "Email") {
                        val intent = Intent().apply {
                            action = Intent.ACTION_SENDTO
                            data = Uri.parse("mailto:tongyz2922@mails.jlu.edu.cn")
                        }
                        context.startActivity(intent)
                    }
                    EntryItem(Icons.Outlined.Check, "Github") {
                        val intent = Intent().apply {
                            action = Intent.ACTION_VIEW
                            data = Uri.parse("https://github.com/tsurumi-yizhou/JiShi-Android")
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
}