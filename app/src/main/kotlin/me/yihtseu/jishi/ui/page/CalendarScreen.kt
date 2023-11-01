@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.ui.component.box.LoadingBox
import me.yihtseu.jishi.ui.component.card.InfoCard
import me.yihtseu.jishi.ui.component.card.LessonCard
import me.yihtseu.jishi.ui.theme.shapes
import me.yihtseu.jishi.utils.time.weeksPast
import me.yihtseu.jishi.vm.CalendarViewModel

@Composable
fun CalendarScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val term by viewModel.term.collectAsState()
    val lessons by viewModel.lessons.collectAsState()
    val host = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val readCalendarPermission = rememberPermissionState(android.Manifest.permission.READ_CALENDAR)
    val writeCalendarPermission = rememberPermissionState(android.Manifest.permission.WRITE_CALENDAR)


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.calendar))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            controller.popBackStack()
                        }
                    ) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (
                                readCalendarPermission.status.isGranted
                                && writeCalendarPermission.status.isGranted
                            ) {
                                viewModel.setCalendar()
                                scope.launch {
                                    host.showSnackbar(context.getString(R.string.success))
                                }
                            } else {
                                readCalendarPermission.launchPermissionRequest()
                                writeCalendarPermission.launchPermissionRequest()
                            }
                        }
                    ) {
                        Icon(Icons.Outlined.Download, null)
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(host)
        },
        floatingActionButton = {
            Button(
                shape = shapes.extraSmall,
                modifier = Modifier.size(60.dp),
                onClick = {
                }
            ) {
                Icon(Icons.Outlined.Add, null)
            }
        }
    ) { paddingValues ->

        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            when (lessons) {
                is State.Success -> (lessons as State.Success).let {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
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

                else -> LoadingBox(modifier = Modifier.padding(paddingValues).fillMaxSize())
            }
        }
    }

    if (term is State.Error) (term as State.Error).let {
        LaunchedEffect(it.message) {
            it.message?.let { message ->
                host.showSnackbar(message)
            }
        }
    }

    if (lessons is State.Error) (lessons as State.Error).let {
        LaunchedEffect(it.message) {
            it.message?.let { message ->
                host.showSnackbar(message)
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.init()
    }
}