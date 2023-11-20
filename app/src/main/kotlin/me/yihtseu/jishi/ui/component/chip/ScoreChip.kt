package me.yihtseu.jishi.ui.component.chip

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.yihtseu.jishi.ui.theme.*

@Composable
fun ScoreChip(
    name: String, type: String, checked: Boolean,
    modifier: Modifier = Modifier,
    onChecked: (Boolean) -> Unit = {},
) {
    ElevatedCard(
        modifier = modifier.padding(HorizontalCardPadding, VerticalChipPadding).fillMaxWidth(),
        shape = shapes.extraSmall
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        onChecked(it)
                    }
                )
                Text(text = type, style = MaterialTheme.typography.labelSmall)
            }
            LazyRow (
                modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1
                    )
                }
            }
        }
    }
}