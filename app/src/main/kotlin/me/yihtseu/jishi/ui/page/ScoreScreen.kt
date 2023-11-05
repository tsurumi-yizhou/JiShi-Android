@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.ui.component.chip.ScoreChip
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.vm.ScoreViewModel

@Composable
fun ScoreScreen(
    controller: NavHostController,
    viewModel: ScoreViewModel = hiltViewModel()
) {
    val gpa by viewModel.gpa.collectAsState()
    val selected by viewModel.selected.collectAsState()
    val subjects by viewModel.subjects.collectAsState()

    val message = remember { mutableStateOf<String?>(null) }
    val host = remember { SnackbarHostState() }
    val context = LocalContext.current

    Compact(
        title = stringResource(R.string.score),
        controller = controller,
        message = message.value,
        loading = subjects is State.Loading
    ) {
        if (subjects is State.Success) (subjects as State.Success).let {
            items(it.data) { subject ->
                ScoreChip(
                    name = subject.name,
                    type = subject.type,
                    checked = selected.contains(subject.name)
                ) {
                    viewModel.select(it, subject.name)
                }
            }
        }
    }

    if (subjects is State.Error) (subjects as State.Error).let {
        LaunchedEffect(it.message) {
            message.value = it.message
        }
    }

    LaunchedEffect(gpa) {
        if (gpa != 0.0) {
            message.value = context.getString(R.string.score_result).format(gpa)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.load()
    }
}