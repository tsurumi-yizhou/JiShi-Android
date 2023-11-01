@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.component.chip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.theme.HorizontalTextPadding
import me.yihtseu.jishi.ui.theme.InnerCardPadding
import me.yihtseu.jishi.ui.theme.typography
import me.yihtseu.jishi.utils.time.format


@Composable
fun DateChip(
    icon: ImageVector, title: String,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    val show = remember { mutableStateOf(false) }
    val state = rememberDatePickerState()
    if (show.value) {
        DatePickerDialog(onDismissRequest = { show.value = false }, confirmButton = {
            Button(onClick = {
                show.value = false
                onClick(format("yyyy-MM-dd", state.selectedDateMillis ?: System.currentTimeMillis()))
            }) {
                Text(text = stringResource(R.string.confirm), style = typography.bodyMedium)
            }
        }) {
            DatePicker(state)
        }
    }
    Row(
        modifier = modifier.clickable { show.value = true },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(icon, null, modifier = Modifier.padding(start = InnerCardPadding))
            Text(
                text = title,
                modifier = Modifier.padding(horizontal = HorizontalTextPadding),
                style = typography.bodyMedium
            )
        }
        Text(
            text = format("yyyy-MM-dd", state.selectedDateMillis ?: System.currentTimeMillis()),
            style = typography.bodyMedium,
            modifier = Modifier.padding(horizontal = HorizontalTextPadding, vertical = InnerCardPadding)
        )
    }
}