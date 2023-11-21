@file:OptIn(ExperimentalLayoutApi::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.ui.theme.HorizontalCardPadding
import me.yihtseu.jishi.ui.theme.VerticalCardPadding
import me.yihtseu.jishi.ui.theme.VerticalChipPadding
import me.yihtseu.jishi.ui.theme.shapes
import me.yihtseu.jishi.vm.SubscriptionViewModel

@Composable
fun SubscriptionScreen(
    controller: NavHostController, viewModel: SubscriptionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val link = rememberSaveable { mutableStateOf("") }

    Compact(
        title = stringResource(R.string.theme_subscription),
        controller = controller,
        message = state.message,
        loading = state.loading,
    ) {
        LazyColumn(
            modifier = Modifier.nestedScroll(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(value = link.value,
                        onValueChange = {
                            link.value = it
                        },
                        modifier = Modifier.padding(HorizontalCardPadding, VerticalCardPadding).fillMaxWidth(),
                        shape = shapes.extraSmall,
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.add(link.value)
                                link.value = ""
                            }) {
                                Icon(Icons.Outlined.Add, null)
                            }
                        })
                    FlowRow(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        state.feeds.forEach {
                            AssistChip(
                                label = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text(text = it.title, style = MaterialTheme.typography.labelSmall)
                                    IconButton(onClick = {
                                        viewModel.sub(it)
                                    }) {
                                        Icon(Icons.Outlined.Delete, null)
                                    }
                                }
                            },
                                onClick = {},
                                enabled = true,
                                modifier = Modifier.padding(HorizontalCardPadding, VerticalChipPadding)
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.init()
    }
}