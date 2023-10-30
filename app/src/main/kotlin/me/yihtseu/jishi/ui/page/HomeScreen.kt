package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AirlineSeatIndividualSuite
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CalendarViewWeek
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.Navigation
import me.yihtseu.jishi.ui.component.EntryCard
import me.yihtseu.jishi.ui.component.EntryItem

@Composable
fun HomeScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            EntryCard(stringResource(R.string.study)) {
                EntryItem(Icons.Outlined.CalendarViewWeek, stringResource(R.string.calendar)) {
                    controller.navigate(Navigation.CalendarScreen.id.toString())
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
            }
        }
    }
}