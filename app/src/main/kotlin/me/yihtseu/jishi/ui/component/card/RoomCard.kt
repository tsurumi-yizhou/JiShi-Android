package me.yihtseu.jishi.ui.component.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.yihtseu.jishi.ui.theme.*

@Composable
fun RoomCard(
    building: String, name: String, duration: String,
    modifier: Modifier = Modifier,
    share: (String) -> Unit,
    onClick: () -> Unit,
) {
    ElevatedCard(
        modifier = modifier
            .padding(HorizontalCardPadding, VerticalCardPadding)
            .fillMaxWidth(),
        shape = shapes.large
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = building,
                    style = typography.labelSmall,
                    modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding),
                    maxLines = 1
                )
                Text(
                    text = name.removePrefix("$building-"),
                    style = typography.titleMedium,
                    modifier = Modifier.padding(horizontal = HorizontalTextPadding),
                    maxLines = 1
                )
                Text(
                    text = duration,
                    style = typography.bodyMedium,
                    modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                )
            }
            IconButton(
                onClick = {
                    share("[$name]: $duration")
                },
                modifier = Modifier.padding(HorizontalChipPadding, VerticalChipPadding)
            ) {
                Icon(Icons.Outlined.Share, null)
            }
        }
    }
}