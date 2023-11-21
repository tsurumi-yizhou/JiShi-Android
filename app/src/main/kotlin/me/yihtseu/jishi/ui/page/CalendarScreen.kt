@file:OptIn(ExperimentalPermissionsApi::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.InfoCard
import me.yihtseu.jishi.ui.component.card.LessonCard
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.utils.time.weeksPast
import me.yihtseu.jishi.vm.CalendarViewModel

@Composable
fun CalendarScreen(
    controller: NavHostController,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val readCalendarPermission = rememberPermissionState(android.Manifest.permission.READ_CALENDAR)
    val writeCalendarPermission = rememberPermissionState(android.Manifest.permission.WRITE_CALENDAR)

    Compact(
        title = stringResource(R.string.calendar),
        controller = controller,
        actions = mapOf(
            Icons.Outlined.Download to {
                if (
                    readCalendarPermission.status.isGranted
                    && writeCalendarPermission.status.isGranted
                ) {
                    viewModel.setCalendar()
                } else {
                    readCalendarPermission.launchPermissionRequest()
                    writeCalendarPermission.launchPermissionRequest()
                }
            }
        ),
        loading = state.loading,
        message = state.message
    ) {
        LazyColumn(
            modifier = Modifier.nestedScroll(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                state.term?.let {
                    InfoCard(
                        text = stringResource(R.string.week_past).format(weeksPast(it.startDate)),
                        button = stringResource(R.string.reload),
                        onClick = { viewModel.init() }
                    )
                }
            }
            state.lessons.forEach { lesson ->
                item {
                    LessonCard(
                        name = lesson.lessonName,
                        teacher = lesson.teacherName.orEmpty(),
                        place = lesson.classroomName.orEmpty(),
                        time = lesson.startLessonNum.toString()
                    ) {
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.init()
    }
}