@file:OptIn(ExperimentalLayoutApi::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.ui.theme.*
import me.yihtseu.jishi.vm.SubscriptionViewModel

@Composable
fun SubscriptionScreen(
    controller: NavHostController,
    viewModel: SubscriptionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val newTopic = rememberSaveable { mutableStateOf("") }
    Compact(
        title = stringResource(R.string.theme_subscription),
        controller = controller,
        message = state.message,
        loading = state.loading
    ) {
        item {
            OutlinedTextField(
                value = newTopic.value,
                onValueChange = { newTopic.value = it },
                shape = shapes.extraSmall,
                leadingIcon = {
                    Icon(Icons.Outlined.Tag, null)
                },
                trailingIcon = {
                IconButton(
                    onClick = {
                        viewModel.add(newTopic.value)
                        newTopic.value = ""
                    }
                ) {
                    Icon(Icons.Outlined.Add, null)
                }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii
                ),
                modifier = Modifier
                    .padding(HorizontalCardPadding, VerticalCardPadding)
                    .fillMaxWidth()
            )
        }
        item {
            FlowRow {
                state.topics.forEach {
                    if (it.isNotBlank()) {
                        AssistChip(
                            onClick = {},
                            label = {
                                Text(text = it, style = typography.labelMedium)
                            },
                            modifier = Modifier.padding(HorizontalChipPadding, VerticalChipPadding)
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.load()
    }
}