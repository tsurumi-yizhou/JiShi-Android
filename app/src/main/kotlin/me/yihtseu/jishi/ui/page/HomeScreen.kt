package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
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
            BottomBar("home", controller)
        }
    ) {
        LazyColumn(
            modifier = Modifier.nestedScroll(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                EntryCard(stringResource(R.string.study)) {
                    EntryItem(Icons.Outlined.CalendarViewWeek, stringResource(R.string.calendar)) {
                        controller.navigate("calendar")
                    }
                    EntryItem(Icons.Outlined.Score, stringResource(R.string.score)) {
                        controller.navigate("score")
                    }
                }
                EntryCard(stringResource(R.string.learn)) {
                    EntryItem(Icons.Outlined.AirlineSeatIndividualSuite, stringResource(R.string.classroom)) {
                        controller.navigate("classrooms")
                    }
                    EntryItem(Icons.Outlined.Book, stringResource(R.string.library)) {
                        controller.navigate("library")
                    }
                }
                EntryCard(stringResource(R.string.life)) {
                    EntryItem(Icons.Outlined.QrCode, stringResource(R.string.qrcode)) {
                        controller.navigate("qrcode")
                    }
                }
            }
        }
    }
}