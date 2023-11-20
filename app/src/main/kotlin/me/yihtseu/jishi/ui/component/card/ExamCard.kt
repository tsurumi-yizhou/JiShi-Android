package me.yihtseu.jishi.ui.component.card

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.yihtseu.jishi.ui.theme.*

@Composable
fun ExamCard(
    name: String, space: String, datetime: String, seat: String? = null, tips: String? = null
) {
    ElevatedCard(
        modifier = Modifier
            .padding(HorizontalCardPadding, VerticalCardPadding)
            .fillMaxWidth(),
        shape = shapes.small
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(
                    HorizontalTextPadding, VerticalTextPadding
                )
            )
            Text(
                text = datetime, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(
                    horizontal = HorizontalTextPadding
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = space, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(
                        horizontal = HorizontalTextPadding
                    )
                )
                Text(
                    text = seat.orEmpty(), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(
                        HorizontalTextPadding
                    )
                )
            }
            Text(
                text = tips.orEmpty(), style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(
                    HorizontalTextPadding, VerticalTextPadding
                )
            )
        }
    }
}