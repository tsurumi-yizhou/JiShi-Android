@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.framework.Compact

@Composable
fun CampusCardScreen(
    controller: NavHostController
) {
    Compact(
        title = stringResource(R.string.campus_card_account),
        controller = controller,
        loading = true,
        message = null
    ) {

    }
}