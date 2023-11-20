package me.yihtseu.jishi.ui.component.card

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.yihtseu.jishi.ui.theme.*

@Composable
fun InfoCard(
    text: String,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.padding(HorizontalCardPadding, VerticalCardPadding).fillMaxWidth(),
        shape = shapes.small
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
            )
        }
    }
}

@Composable
fun InfoCard(
    text: String,
    modifier: Modifier = Modifier,
    button: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier.padding(HorizontalCardPadding, VerticalCardPadding).fillMaxWidth(),
        shape = shapes.small
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
            )

            OutlinedButton(
                shape = shapes.extraSmall,
                onClick = onClick,
                modifier = Modifier
                    .padding(HorizontalTextPadding, VerticalTextPadding)
                    .fillMaxWidth()
            ) {
                Text(text = button, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
fun InfoCard(
    text: String,
    modifier: Modifier = Modifier,
    buttonLeft: String,
    onClickLeft: () -> Unit,
    buttonRight: String,
    onClickRight: () -> Unit
) {
    ElevatedCard(
        modifier = modifier.padding(HorizontalCardPadding, VerticalCardPadding).fillMaxWidth(),
        shape = shapes.small
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    shape = shapes.extraSmall,
                    onClick = onClickLeft,
                    modifier = Modifier
                        .padding(HorizontalTextPadding, VerticalTextPadding)
                        .weight(.5f)
                ) {
                    Text(text = buttonLeft, style = MaterialTheme.typography.labelMedium)
                }
                OutlinedButton(
                    shape = shapes.extraSmall,
                    onClick = onClickRight,
                    modifier = Modifier
                        .padding(HorizontalTextPadding, VerticalTextPadding)
                        .weight(.5f)
                ) {
                    Text(text = buttonRight, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}