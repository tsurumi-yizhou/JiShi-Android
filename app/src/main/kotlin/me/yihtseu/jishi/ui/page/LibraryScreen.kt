@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.EntryCard
import me.yihtseu.jishi.ui.component.card.EntryItem
import me.yihtseu.jishi.vm.LibraryViewModel

@Composable
fun LibraryScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.library))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            controller.popBackStack()
                        }
                    ) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            EntryCard(stringResource(R.string.floor2)) {
                EntryItem(Icons.Outlined.OpenInFull, stringResource(R.string.open_space) + " B") {}
            }
            EntryCard(stringResource(R.string.floor3)) {
                EntryItem(Icons.Outlined.Book, stringResource(R.string.read_space) + " A") {}
                EntryItem(Icons.Outlined.Book, stringResource(R.string.read_space) + " B") {}
            }
            EntryCard(stringResource(R.string.floor4)) {
                EntryItem(Icons.Outlined.Article, stringResource(R.string.journal_space) + " A") {}
                EntryItem(Icons.Outlined.Article, stringResource(R.string.journal_space) + " A") {}
            }
            EntryCard(stringResource(R.string.floor5)) {
                EntryItem(Icons.Outlined.Interests, stringResource(R.string.interactif_space) + " A"){}
                EntryItem(Icons.Outlined.Interests, stringResource(R.string.interactif_space) + " B"){}
            }
        }
    }
}