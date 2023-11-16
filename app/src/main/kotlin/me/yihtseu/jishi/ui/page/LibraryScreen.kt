@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Interests
import androidx.compose.material.icons.outlined.OpenInFull
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.component.card.EntryCard
import me.yihtseu.jishi.ui.component.card.EntryItem
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.vm.LibraryViewModel

@Composable
fun LibraryScreen(
    controller: NavHostController,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    Compact(
        title = stringResource(R.string.library),
        controller = controller,
        message = null,
        loading = false
    ) {
        LazyColumn(
            modifier = Modifier.nestedScroll(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                EntryCard(stringResource(R.string.floor2)) {
                    EntryItem(Icons.Outlined.OpenInFull, stringResource(R.string.open_space) + " B") {}
                }
            }
            item {
                EntryCard(stringResource(R.string.floor3)) {
                    EntryItem(Icons.Outlined.Book, stringResource(R.string.read_space) + " A") {}
                    EntryItem(Icons.Outlined.Book, stringResource(R.string.read_space) + " B") {}
                }
            }
            item {
                EntryCard(stringResource(R.string.floor4)) {
                    EntryItem(Icons.Outlined.Article, stringResource(R.string.journal_space) + " A") {}
                    EntryItem(Icons.Outlined.Article, stringResource(R.string.journal_space) + " B") {}
                }
            }
            item {
                EntryCard(stringResource(R.string.floor5)) {
                    EntryItem(Icons.Outlined.Interests, stringResource(R.string.interactif_space) + " A") {}
                    EntryItem(Icons.Outlined.Interests, stringResource(R.string.interactif_space) + " B") {}
                }
            }
        }
    }
}