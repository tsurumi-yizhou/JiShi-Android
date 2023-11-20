package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.ExamCard
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.vm.ExamViewModel

@Composable
fun ExamScreen(
    controller: NavHostController,
    viewModel: ExamViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    Compact(
        title = stringResource(R.string.exam_info),
        controller = controller,
        loading = state.loading,
        message = state.message
    ) {
        LazyColumn(
            modifier = Modifier.nestedScroll(it)
        ) {
            items(state.exams) {
                ExamCard(it.exam, it.classroom, it.datetime, it.seat, it.tips)
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.init()
    }
}