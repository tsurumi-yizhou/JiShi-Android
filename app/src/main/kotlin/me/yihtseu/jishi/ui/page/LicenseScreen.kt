@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Adjust
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.EntryCard
import me.yihtseu.jishi.ui.component.card.EntryItem

@Composable
fun LicenseScreen(
    controller: NavHostController
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.opensource_license))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            controller.popBackStack()
                        }
                    ) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }

            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EntryCard(stringResource(R.string.opensource_license)) {
                EntryItem(Icons.Outlined.Adjust, "MPL 2.0") {
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse("https://www.mozilla.org/en-US/MPL/2.0/")
                    }
                    context.startActivity(intent)
                }
                EntryItem(Icons.Outlined.Adjust, "BSD 3.0") {
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse("https://opensource.org/license/bsd-3-clause/")
                    }
                    context.startActivity(intent)
                }
                EntryItem(Icons.Outlined.Adjust, "Apache 2.0") {
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}