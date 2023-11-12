@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package me.yihtseu.jishi.ui.component.chip

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import me.yihtseu.jishi.ui.theme.HorizontalChipPadding
import me.yihtseu.jishi.ui.theme.VerticalChipPadding
import me.yihtseu.jishi.ui.theme.shapes
import me.yihtseu.jishi.ui.theme.typography

@Composable
fun ChipGroup(
    chips: List<String>, modifier: Modifier = Modifier,
    onSelect: (String) -> Unit
) {
    val selected = rememberSaveable { mutableStateOf<String?>(null) }
    FlowRow {
        chips.forEach { chip ->
            FilterChip(
                selected = selected.value == chip,
                enabled = true,
                shape = shapes.large,
                modifier = Modifier.padding(HorizontalChipPadding, VerticalChipPadding),
                onClick = {
                    selected.value = chip
                    onSelect(chip)
                },
                label = {
                    Text(chip, style = typography.labelMedium)
                },
            )
        }
    }
}