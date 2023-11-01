package me.yihtseu.jishi.ui.component.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import me.yihtseu.jishi.ui.theme.*


@Composable
fun NewsCard(
    title: String, origin: String?, desc: String?, image: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .padding(HorizontalCardPadding, VerticalCardPadding)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = shapes.large
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            image?.let {
                AsyncImage(
                    it, null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(vertical = VerticalTextPadding)
                        .size(ImageWidth, ImageHeight)
                        .clip(shapes.extraLarge)
                )
            }
            Column (
                horizontalAlignment = Alignment.Start
            ) {
                origin?.let {
                    Text(
                        text = it, maxLines = 2, minLines = 1,
                        style = typography.labelSmall,
                        modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                    )
                }
                Text(
                    text = title, style = typography.titleMedium, maxLines = 2, minLines = 1,
                    modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                )
                desc?.let {
                    Text(
                        text = it, maxLines = 2, minLines = 2,
                        style = typography.bodySmall,
                        modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding),
                    )
                }
            }
        }
    }
}