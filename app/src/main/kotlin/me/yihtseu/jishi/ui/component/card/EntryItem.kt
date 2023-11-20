package me.yihtseu.jishi.ui.component.card

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import me.yihtseu.jishi.ui.theme.HorizontalTextPadding
import me.yihtseu.jishi.ui.theme.InnerCardPadding
import me.yihtseu.jishi.ui.theme.InputWidth

@Composable
fun EntryItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.padding().fillMaxWidth().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, modifier = Modifier.padding(start = InnerCardPadding))
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = HorizontalTextPadding, vertical = InnerCardPadding),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun EntryItem(icon: Uri, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.padding().fillMaxWidth().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(ImageRequest.Builder(LocalContext.current).data(icon).transformations(CircleCropTransformation()).build(), contentDescription = null, modifier = Modifier.size(48.dp).padding(start = InnerCardPadding))
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = HorizontalTextPadding, vertical = InnerCardPadding),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun EntryItem(icon: ImageVector, title: String, detail: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.padding().fillMaxWidth().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, modifier = Modifier.padding(start = InnerCardPadding))

        Column(
            modifier = Modifier.padding(vertical = InnerCardPadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(horizontal = HorizontalTextPadding),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = detail,
                modifier = Modifier.padding(horizontal = HorizontalTextPadding),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun EntryItem(icon: Uri, title: String, detail: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.padding().fillMaxWidth().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(ImageRequest.Builder(LocalContext.current).data(icon).transformations(CircleCropTransformation()).build(), contentDescription = null, modifier = Modifier.size(48.dp).padding(start = InnerCardPadding))

        Column(
            modifier = Modifier.padding(vertical = InnerCardPadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(horizontal = HorizontalTextPadding),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = detail,
                modifier = Modifier.padding(horizontal = HorizontalTextPadding),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun EntryInput(
    icon: ImageVector,
    title: String,
    detail: String? = null,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Number,
    onValueChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding().fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, modifier = Modifier.padding(start = InnerCardPadding))
            Column(
                modifier = Modifier.padding(vertical = InnerCardPadding),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    modifier = Modifier.padding(horizontal = HorizontalTextPadding),
                    style = MaterialTheme.typography.bodyMedium
                )
                detail?.let { detail ->
                    Text(
                        text = detail,
                        modifier = Modifier.padding(horizontal = HorizontalTextPadding),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = Modifier
                .padding(horizontal = HorizontalTextPadding, vertical = InnerCardPadding)
                .width(InputWidth),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            textStyle = MaterialTheme.typography.bodySmall
        )
    }
}