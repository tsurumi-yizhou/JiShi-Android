package me.yihtseu.jishi.ui.component.box

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val awaitSentences = arrayOf(
    "请坐和放宽，马上就好",
    "好东西就要来了。",
    "你正在成功！你已完成100%。",
    "海内存知己，天涯若比邻。\n请稍等…",
    "逝者如斯夫，不舍昼夜。\n请稍候，我们即将完成。",
    "路漫漫其修远兮，吾将上下而求索。\n这可能需要一段时间。",
    "山重水复疑无路，柳暗花明又一村。\n我们仍在处理，请耐心等待。",
    "春风得意马蹄疾，一日看尽长安花。\n现在，我们将开始设置你的应用。",
)

@Composable
fun LoadingBox(
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val visibility = remember { mutableStateOf(true) }
    val slogan = remember { mutableStateOf(awaitSentences.random()) }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item { CircularProgressIndicator() }
        item {
            AnimatedVisibility(
                visible = visibility.value,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Text(text = slogan.value, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
    LaunchedEffect(coroutineScope) {
        coroutineScope.launch {
            while (true) {
                visibility.value = false
                delay(1000)
                slogan.value = awaitSentences.random()
                visibility.value = true
                delay(5000)
            }
        }
    }
}