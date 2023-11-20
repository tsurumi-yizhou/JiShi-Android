package me.yihtseu.jishi.ui.component.box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.campus.edu.EduBuilding
import me.yihtseu.jishi.ui.component.card.EntryCard
import me.yihtseu.jishi.ui.component.chip.ChipGroup
import me.yihtseu.jishi.ui.component.chip.DateChip
import me.yihtseu.jishi.ui.component.chip.LessonChip
import me.yihtseu.jishi.utils.time.format

val zones = listOf("前卫", "南岭", "新民", "朝阳", "南湖", "和平")
@Composable
fun QueryDrawer(
    modifier: Modifier = Modifier,
    buildings: List<EduBuilding>,
    query: (zone: String, code: String, date: String, start: Int, end: Int) -> Unit
) {
    val start = rememberSaveable { mutableIntStateOf(3) }
    val end = rememberSaveable { mutableIntStateOf(5) }
    val date = rememberSaveable { mutableStateOf(format("yyyy-MM-dd", System.currentTimeMillis())) }
    val zone = rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EntryCard(stringResource(R.string.time)) {
            DateChip(
                Icons.Outlined.DateRange,
                stringResource(R.string.date_select),
                modifier = Modifier.fillMaxWidth()
            ) {
                date.value = it
            }
            LessonChip(
                Icons.Outlined.Timeline,
                stringResource(R.string.start_select),
                modifier = Modifier.fillMaxWidth()
            ) {
                start.intValue = it
            }
            LessonChip(
                Icons.Outlined.Timeline,
                stringResource(R.string.end_select),
                modifier = Modifier.fillMaxWidth()
            ) {
                end.intValue = it
            }
        }

        EntryCard(stringResource(R.string.location)) {
            LazyColumn {
                item {
                    ChipGroup(
                        chips = buildings
                            .filter { it.zone == zone.intValue }
                            .map { it.name }
                            .sortedBy { it.length },
                        modifier = Modifier.fillMaxWidth()
                    ) { name ->
                        buildings.find { it.name == name }?.let {
                            query("0${zone.intValue}", it.id, date.value, start.intValue, end.intValue)
                        }
                    }
                }
                item {
                    ChipGroup(
                        chips = zones,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        zone.intValue = zones.indexOf(it) + 1
                    }
                }
            }
        }
    }
}