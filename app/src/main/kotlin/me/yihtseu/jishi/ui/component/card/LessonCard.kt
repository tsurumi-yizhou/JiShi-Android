package me.yihtseu.jishi.ui.component.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.yihtseu.jishi.ui.theme.*

@Composable
fun LessonCard(
    modifier: Modifier = Modifier,
    name: String, teacher: String, place: String, time: String,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier
            .padding(HorizontalCardPadding, VerticalCardPadding/2)
            .fillMaxWidth(),
        shape = shapes.small
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
            )
            Text(
                text = teacher,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
            )
        }
    }
}