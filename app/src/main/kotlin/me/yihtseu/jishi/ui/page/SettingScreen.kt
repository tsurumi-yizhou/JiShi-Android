package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.EntryCard
import me.yihtseu.jishi.ui.component.card.EntryItem

@Composable
fun SettingScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            EntryCard(stringResource(R.string.account)) {
                EntryItem(Icons.Outlined.School, stringResource(R.string.campus_account), ) {}
                EntryItem(Icons.Outlined.CardGiftcard, stringResource(R.string.campus_card_account), ) {}
            }
        }
        item {
            EntryCard(stringResource(R.string.news)) {
                EntryItem(Icons.Outlined.Subscriptions, stringResource(R.string.theme_subscription), ) {}
            }
        }
        item {
            EntryCard(stringResource(R.string.about)) {
                EntryItem(Icons.Outlined.Info, stringResource(R.string.about_this_app), { })
                EntryItem(Icons.Outlined.Adjust, stringResource(R.string.opensource_license), ) {}
                EntryItem(Icons.Outlined.Accessible, stringResource(R.string.privacy), ) {}
                EntryItem(Icons.Outlined.Update, stringResource(R.string.check_update), ) {}
            }
        }
    }
}