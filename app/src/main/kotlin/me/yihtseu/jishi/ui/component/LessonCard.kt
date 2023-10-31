package me.yihtseu.jishi.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.yihtseu.jishi.ui.theme.HorizontalCardPadding
import me.yihtseu.jishi.ui.theme.VerticalCardPadding
import me.yihtseu.jishi.ui.theme.shapes
import me.yihtseu.jishi.ui.theme.typography

@Composable
fun LessonCard(
    modifier: Modifier = Modifier,
    name: String, teacher: String, place: String, time: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier.padding(HorizontalCardPadding, VerticalCardPadding).clickable { onClick() },
        shape = shapes.large
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = name, style = typography.labelLarge)
            Text(text = teacher, style = typography.bodyMedium)
        }
    }
}