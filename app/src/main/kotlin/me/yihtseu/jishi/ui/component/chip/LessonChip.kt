package me.yihtseu.jishi.ui.component.chip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import me.yihtseu.jishi.ui.theme.HorizontalTextPadding
import me.yihtseu.jishi.ui.theme.InnerCardPadding
import me.yihtseu.jishi.ui.theme.typography


@Composable
fun LessonChip(
    icon: ImageVector, title: String,
    modifier: Modifier = Modifier,
    onSelect: (Int) -> Unit
) {
    val expand = remember { mutableStateOf(false) }
    val selected = rememberSaveable { mutableStateOf(0) }
    Row(
        modifier = modifier.clickable { expand.value = !expand.value },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(icon, null, modifier = Modifier.padding(start = InnerCardPadding))
            Text(text = title, modifier = Modifier.padding(horizontal = HorizontalTextPadding), style = typography.bodyMedium)
        }

        Box {
            Text(text = selected.value.toString(), style = typography.bodyMedium, modifier = Modifier.padding(horizontal = HorizontalTextPadding, vertical = InnerCardPadding))
            DropdownMenu(
                expanded = expand.value,
                onDismissRequest = { expand.value = false }
            ) {
                repeat(13) {
                    DropdownMenuItem(
                        text = { Text((it + 1).toString()) },
                        modifier = Modifier.clickable {  },
                        onClick = {
                            selected.value = it + 1
                            onSelect(it + 1)
                            expand.value = false
                        }
                    )
                }
            }
        }
    }
}