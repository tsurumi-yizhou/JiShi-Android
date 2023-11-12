package me.yihtseu.jishi.ui.page

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.Navigation
import me.yihtseu.jishi.ui.component.card.EntryCard
import me.yihtseu.jishi.ui.component.card.EntryItem
import me.yihtseu.jishi.ui.framework.BottomBar
import me.yihtseu.jishi.ui.framework.Compact

@Composable
fun HomeScreen(
    controller: NavHostController,
) {
    Compact(
        title = stringResource(R.string.home),
        loading = false,
        message = null,
        bottom = {
            BottomBar(Navigation.HomeScreen.id.toString(), controller)
        }
    ) {
        item {
            EntryCard(stringResource(R.string.study)) {
                EntryItem(Icons.Outlined.CalendarViewWeek, stringResource(R.string.calendar)) {
                    controller.navigate(Navigation.CalendarScreen.id.toString())
                }
                EntryItem(Icons.Outlined.Score, stringResource(R.string.score)) {
                    controller.navigate(Navigation.ScoreScreen.id.toString())
                }
            }
            EntryCard(stringResource(R.string.learn)) {
                EntryItem(Icons.Outlined.AirlineSeatIndividualSuite, stringResource(R.string.classroom)) {
                    controller.navigate(Navigation.ClassroomScreen.id.toString())
                }
                EntryItem(Icons.Outlined.Book, stringResource(R.string.library)) {
                    controller.navigate(Navigation.LibraryScreen.id.toString())
                }
            }
            EntryCard(stringResource(R.string.life)) {
                EntryItem(Icons.Outlined.QrCode, stringResource(R.string.qrcode)) {
                    controller.navigate(Navigation.IdentifyScreen.id.toString())
                }
                EntryItem(Icons.Outlined.Nfc, stringResource(R.string.nfc_card)) {
                    controller.navigate(Navigation.NfcCardScreen.id.toString())
                }
            }
        }
    }
}