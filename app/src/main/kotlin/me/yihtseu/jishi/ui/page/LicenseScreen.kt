@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.EntryCard
import me.yihtseu.jishi.ui.component.card.EntryItem
import me.yihtseu.jishi.ui.framework.Compact

@Composable
fun LicenseScreen(
    controller: NavHostController
) {
    val context = LocalContext.current

    Compact(
        title = stringResource(R.string.opensource_license),
        message = null,
        loading = false,
        controller = controller
    ) {
        item {
            EntryCard(stringResource(R.string.opensource_license)) {
                EntryItem(Icons.Outlined.Bookmark, "MPL 2.0") {
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse("https://www.mozilla.org/en-US/MPL/2.0/")
                    }
                    context.startActivity(intent)
                }
                EntryItem(Icons.Outlined.Bookmark, "BSD 3.0") {
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse("https://opensource.org/license/bsd-3-clause/")
                    }
                    context.startActivity(intent)
                }
                EntryItem(Icons.Outlined.Bookmark, "Apache 2.0") {
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