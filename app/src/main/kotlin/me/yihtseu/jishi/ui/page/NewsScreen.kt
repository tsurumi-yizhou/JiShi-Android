package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.ui.component.box.LoadingBox
import me.yihtseu.jishi.vm.NewsViewModel

@Composable
fun NewsScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {
    LoadingBox(modifier = Modifier.fillMaxSize())
}