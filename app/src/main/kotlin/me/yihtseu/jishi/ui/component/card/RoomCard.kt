package me.yihtseu.jishi.ui.component.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
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
        shape = shapes.small
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = building,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                    )
                    Text(
                        text = name.removePrefix("$building-"),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = HorizontalTextPadding)
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
            Text(
                text = duration,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
            )
        }
    }
}