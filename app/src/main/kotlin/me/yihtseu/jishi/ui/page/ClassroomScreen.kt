@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.campus.edu.Building
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.ui.component.box.QueryDrawer
import me.yihtseu.jishi.ui.component.card.RoomCard
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.utils.system.share
import me.yihtseu.jishi.vm.ClassroomViewModel

@Composable
fun ClassroomScreen(
    controller: NavHostController,
    viewModel: ClassroomViewModel = hiltViewModel()
) {
    val message = remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(true) }

    val buildings by viewModel.buildings.collectAsState()
    val classrooms by viewModel.classrooms.collectAsState()

    Compact(
        title = stringResource(R.string.classroom),
        controller = controller,
        message = message.value,
        loading = classrooms is State.Loading,
        drawer = {
            if (buildings is State.Success) {
                QueryDrawer(
                    modifier = Modifier.fillMaxWidth(),
                    buildings = (buildings as State.Success).data.map {
                        Building(
                            zone = it.otherFields.zoneCode.toInt(),
                            id = it.id,
                            name = it.name
                        )
                    }
                ) { zone, code, date, start, end ->
                    viewModel.query(zone, code, date, start, end)
                    showBottomSheet.value = false
                }
            }
        }
    ) {
        if (classrooms is State.Success) (classrooms as State.Success).let {
            items(it.data) {
                RoomCard(
                    building = it.building,
                    name = it.name,
                    duration = it.duration,
                    share = { share(context, it) },
                    onClick = {}
                )
            }
        }
    }

    if (buildings is State.Error) {
        (buildings as State.Error).message?.let {
            LaunchedEffect(it) {
                message.value = it
            }
        }
    }

    if (classrooms is State.Error) {
        (classrooms as State.Error).message?.let {
            LaunchedEffect(it) {
                message.value = it
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.init()
    }
}