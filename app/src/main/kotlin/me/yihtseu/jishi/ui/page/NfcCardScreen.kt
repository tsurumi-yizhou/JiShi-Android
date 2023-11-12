package me.yihtseu.jishi.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.InfoCard
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.vm.NfcCardViewModel

@Composable
fun NfcCardScreen(
    controller: NavHostController,
    viewModel: NfcCardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    Compact(
        title = stringResource(R.string.nfc_card),
        controller = controller,
        loading = state.loading,
        message = state.message
    ) {
        item {
            if (state.cardId.isEmpty()) {
                InfoCard(
                    text = stringResource(R.string.nfc_read_mode)
                )
            } else {
                InfoCard(
                    text = stringResource(R.string.nfc_write_mode),
                    button = stringResource(R.string.delete),
                ) {
                    viewModel.delete()
                }
            }
        }
    }
    LaunchedEffect(viewModel) {
        viewModel.load()
    }
}