@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.jishi.State
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
    val term by viewModel.term.collectAsState()
    val lessons by viewModel.lessons.collectAsState()

    val message = remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

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
                    message.value = context.getString(R.string.success)
                } else {
                    readCalendarPermission.launchPermissionRequest()
                    writeCalendarPermission.launchPermissionRequest()
                }
            }
        ),
        loading = lessons is State.Error,
        message = message.value
    ) {
        item {
            if (term is State.Success) (term as State.Success).let {
                InfoCard(
                    text = stringResource(R.string.week_past).format(weeksPast(it.data.startDate)),
                    button = stringResource(R.string.reload),
                    onClick = { viewModel.init() }
                )
            }
        }
        if (lessons is State.Success) (lessons as State.Success).let {
            items(it.data) { lesson ->
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

    if (term is State.Error) (term as State.Error).let {
        LaunchedEffect(it.message) {
            message.value = it.message
        }
    }

    if (lessons is State.Error) (lessons as State.Error).let {
        LaunchedEffect(it.message) {
            message.value = it.message
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.init()
    }
}