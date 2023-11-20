package me.yihtseu.jishi.ui.component.card

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.yihtseu.jishi.ui.theme.HorizontalCardPadding
import me.yihtseu.jishi.ui.theme.InnerCardPadding
import me.yihtseu.jishi.ui.theme.VerticalCardPadding
import me.yihtseu.jishi.ui.theme.shapes

@Composable
fun EntryCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = HorizontalCardPadding, vertical = VerticalCardPadding)
            .fillMaxWidth(),
        shape = shapes.small
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(start = InnerCardPadding, top = InnerCardPadding),
            style = MaterialTheme.typography.labelMedium
        )
        content()
        // Spacer(modifier = Modifier.height(InnerCardPadding))
    }
}