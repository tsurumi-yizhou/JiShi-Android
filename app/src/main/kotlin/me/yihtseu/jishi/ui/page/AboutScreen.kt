@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContactMail
import androidx.compose.material.icons.outlined.ContactSupport
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.BuildConfig
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.EntryCard
import me.yihtseu.jishi.ui.component.card.EntryItem
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.ui.theme.HorizontalCardPadding
import me.yihtseu.jishi.ui.theme.HorizontalTextPadding
import me.yihtseu.jishi.ui.theme.VerticalCardPadding
import me.yihtseu.jishi.ui.theme.VerticalTextPadding
import me.yihtseu.jishi.vm.AboutViewModel

@Composable
fun AboutScreen(
    controller: NavHostController,
    viewModel: AboutViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    Compact(
        title = stringResource(R.string.about_this_app),
        controller = controller,
        actions = mapOf(
            Icons.Outlined.Info to {
                val intent = Intent().apply {
                    action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.parse("package:${context.packageName}")
                }
                context.startActivity(intent)
            }
        ),
        loading = false,
        message = null
    ) {
        LazyColumn(
            modifier = Modifier.nestedScroll(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
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
                            text = stringResource(R.string.app_name), style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                        )
                        Text(
                            text = "${stringResource(R.string.version)}: ${BuildConfig.VERSION_NAME}",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(HorizontalTextPadding, 0.dp)
                        )
                        Text(
                            text = stringResource(R.string.app_desc), style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                        )
                    }
                }
            }

            item {
                EntryCard(stringResource(R.string.contact)) {
                    EntryItem(Icons.Outlined.ContactMail, "Email") {
                        val intent = Intent().apply {
                            action = Intent.ACTION_SENDTO
                            data = Uri.parse("mailto:tongyz2922@mails.jlu.edu.cn")
                        }
                        context.startActivity(intent)
                    }
                    EntryItem(Icons.Outlined.ContactSupport, "Github") {
                        val intent = Intent().apply {
                            action = Intent.ACTION_VIEW
                            data = Uri.parse("https://github.com/tsurumi-yizhou/JiShi-Android")
                        }
                        context.startActivity(intent)
                    }
                }
            }

            item {
                if (state.contributors.isNotEmpty()) {
                    EntryCard(stringResource(R.string.contributors)) {
                        state.contributors.forEach {
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
        }
    }

    LaunchedEffect(Unit) {
        viewModel.init()
    }
}