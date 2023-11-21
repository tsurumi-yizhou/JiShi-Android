package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.EntryCard
import me.yihtseu.jishi.ui.component.card.EntryItem
import me.yihtseu.jishi.ui.framework.BottomBar
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.vm.SettingViewModel

@Composable
fun SettingScreen(
    controller: NavHostController,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Compact(
        title = stringResource(R.string.setting),
        loading = false,
        message = state.message,
        action = state.url,
        bottom = {
            BottomBar("setting", controller)
        }
    ) {
        LazyColumn(
            modifier = Modifier.nestedScroll(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                EntryCard(stringResource(R.string.account)) {
                    EntryItem(Icons.Outlined.School, stringResource(R.string.campus_account)) {
                        controller.navigate("campus_email")
                    }
                    EntryItem(Icons.Outlined.CardGiftcard, stringResource(R.string.campus_card_account)) {
                        controller.navigate("campus_card")
                    }
                }
            }
            item {
                EntryCard(stringResource(R.string.network)) {
                    Box {
                        val showDropdownMenu = remember { mutableStateOf(false) }
                        EntryItem(Icons.Outlined.NetworkPing, stringResource(R.string.heartbeat)) {
                            showDropdownMenu.value = !showDropdownMenu.value
                        }
                        DropdownMenu(
                            expanded = showDropdownMenu.value,
                            onDismissRequest = { showDropdownMenu.value = false },
                        ) {
                            DropdownMenuItem(text = {
                                Text(text = "10s", style = MaterialTheme.typography.bodyMedium)
                            }, onClick = {
                                viewModel.setHeartbeat(10)
                                showDropdownMenu.value = false
                            })
                            DropdownMenuItem(text = {
                                Text(text = "15s", style = MaterialTheme.typography.bodyMedium)
                            }, onClick = {
                                viewModel.setHeartbeat(15)
                                showDropdownMenu.value = false
                            })
                            DropdownMenuItem(text = {
                                Text(text = "30s", style = MaterialTheme.typography.bodyMedium)
                            }, onClick = {
                                viewModel.setHeartbeat(30)
                                showDropdownMenu.value = false
                            })
                        }
                    }
                    Box {
                        val showDropdownMenu = remember { mutableStateOf(false) }
                        EntryItem(Icons.Outlined.NetworkCheck, stringResource(R.string.timeout)) {
                            showDropdownMenu.value = !showDropdownMenu.value
                        }
                        DropdownMenu(
                            expanded = showDropdownMenu.value,
                            onDismissRequest = { showDropdownMenu.value = false },
                        ) {
                            DropdownMenuItem(text = {
                                Text(text = "10s", style = MaterialTheme.typography.bodyMedium)
                            }, onClick = {
                                viewModel.setHeartbeat(10)
                                showDropdownMenu.value = false
                            })
                            DropdownMenuItem(text = {
                                Text(text = "15s", style = MaterialTheme.typography.bodyMedium)
                            }, onClick = {
                                viewModel.setHeartbeat(15)
                                showDropdownMenu.value = false
                            })
                            DropdownMenuItem(text = {
                                Text(text = "30s", style = MaterialTheme.typography.bodyMedium)
                            }, onClick = {
                                viewModel.setHeartbeat(30)
                                showDropdownMenu.value = false
                            })
                        }
                    }
                }

            }
            item {
                EntryCard(stringResource(R.string.news)) {
                    EntryItem(Icons.Outlined.Subscriptions, stringResource(R.string.theme_subscription)) {
                        controller.navigate("subscription")
                    }
                }
            }
            item {
                EntryCard(stringResource(R.string.about)) {
                    EntryItem(Icons.Outlined.Info, stringResource(R.string.about_this_app)) {
                        controller.navigate("about")
                    }
                    EntryItem(Icons.Outlined.Bookmark, stringResource(R.string.opensource_license)) {
                        controller.navigate("license")
                    }
                    EntryItem(Icons.Outlined.Update, stringResource(R.string.check_update)) {
                        viewModel.checkUpdate()
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.init()
    }
}