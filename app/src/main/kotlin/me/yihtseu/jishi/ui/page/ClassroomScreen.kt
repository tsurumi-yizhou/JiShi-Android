@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.campus.edu.EduBuilding
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
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Compact(
        title = stringResource(R.string.classroom),
        controller = controller,
        message = state.message,
        loading = state.loading,
        drawer = {
            QueryDrawer(
                modifier = Modifier.fillMaxWidth(),
                buildings = state.buildings.map {
                    EduBuilding(
                        zone = it.otherFields.zoneCode.toInt(),
                        id = it.id,
                        name = it.name
                    )
                }
            ) { zone, code, date, start, end ->
                viewModel.query(zone, code, date, start, end)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.nestedScroll(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(state.classrooms) {
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

    LaunchedEffect(viewModel) {
        viewModel.init()
    }
}