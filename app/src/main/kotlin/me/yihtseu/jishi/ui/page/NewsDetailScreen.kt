package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.ui.framework.Html
import me.yihtseu.jishi.ui.theme.HorizontalTextPadding
import me.yihtseu.jishi.ui.theme.VerticalCardPadding
import me.yihtseu.jishi.vm.NewsDetailViewModel
import org.jsoup.Jsoup

@Composable
fun NewsDetailScreen(
    controller: NavHostController,
    viewModel: NewsDetailViewModel = hiltViewModel()
) {
    viewModel.init()
    val state by viewModel.state.collectAsState()

    Compact(
        title = state.title,
        controller = controller,
        loading = false,
        message = null
    ) {
        LazyColumn(
            modifier = Modifier.nestedScroll(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Jsoup.parse(state.content).body().children().forEach {
                item {
                    Html(it, modifier = Modifier.padding(HorizontalTextPadding, VerticalCardPadding))
                }
            }
        }
    }
}