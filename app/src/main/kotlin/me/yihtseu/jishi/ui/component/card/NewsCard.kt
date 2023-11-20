package me.yihtseu.jishi.ui.component.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.yihtseu.jishi.ui.theme.*


@Composable
fun NewsCard(
    title: String, time: String, desc: String?, image: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .padding(HorizontalCardPadding, VerticalCardPadding)
            .fillMaxWidth(),
        shape = shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            image?.let {
                AsyncImage(
                    it, null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(ImagePadding)
                        .size(ImageWidth, ImageHeight)
                )
            }
            Column (
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title, style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(HorizontalTextPadding, 0.dp)
                )
                desc?.let {
                    Text(
                        text = it + "...", maxLines = 4,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding),
                    )
                }
            }
        }
    }
}