package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.Navigation
import me.yihtseu.jishi.ui.component.card.EntryCard
import me.yihtseu.jishi.ui.component.card.EntryItem
import me.yihtseu.jishi.vm.SettingViewModel

@Composable
fun SettingScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            EntryCard(stringResource(R.string.account)) {
                EntryItem(Icons.Outlined.School, stringResource(R.string.campus_account)) {
                    controller.navigate(Navigation.CampusEmailScreen.id.toString())
                }
                EntryItem(Icons.Outlined.CardGiftcard, stringResource(R.string.campus_card_account)) {
                    controller.navigate(Navigation.CampusCardScreen.id.toString())
                }
            }
        }
        item {
            EntryCard(stringResource(R.string.news)) {
                EntryItem(Icons.Outlined.Subscriptions, stringResource(R.string.theme_subscription)) {
                    controller.navigate(Navigation.SubscriptionScreen.id.toString())
                }
            }
        }
        item {
            EntryCard(stringResource(R.string.about)) {
                EntryItem(Icons.Outlined.Info, stringResource(R.string.about_this_app)) {
                    controller.navigate(Navigation.AboutScreen.id.toString())
                }
                EntryItem(Icons.Outlined.Adjust, stringResource(R.string.opensource_license)) {
                    controller.navigate(Navigation.LicenseScreen.id.toString())
                }
            }
        }
    }
}