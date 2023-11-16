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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.chip.ScoreChip
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.vm.ScoreViewModel

@Composable
fun ScoreScreen(
    controller: NavHostController,
    viewModel: ScoreViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Compact(
        title = stringResource(R.string.score),
        controller = controller,
        message = state.message,
        loading = state.loading
    ) {
        LazyColumn(
            modifier = Modifier.nestedScroll(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(state.subjects) { subject ->
                ScoreChip(
                    name = subject.name,
                    type = subject.type,
                    checked = state.selected.contains(subject.name)
                ) {
                    viewModel.select(it, subject.name)
                }
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.load()
    }
}