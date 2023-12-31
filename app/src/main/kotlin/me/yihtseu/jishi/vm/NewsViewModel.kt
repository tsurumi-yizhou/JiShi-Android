@file:OptIn(ExperimentalPagingApi::class)

package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.news.Entry
import me.yihtseu.jishi.model.news.Feed
import me.yihtseu.jishi.repo.news.EntryDao
import me.yihtseu.jishi.repo.news.FeedDao
import me.yihtseu.jishi.repo.news.RssRemoteMediator
import javax.inject.Inject

data class NewsState(
    val loading: Boolean = true,
    val message: String? = null,
    val feeds: List<Feed> = emptyList(),
    val entries: Flow<PagingData<Entry>>? = null
)

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val feedDao: FeedDao,
    private val entryDao: EntryDao
) : ViewModel() {
    private val _state = MutableStateFlow(NewsState())
    val state = _state.asStateFlow()
    fun init() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val feeds = feedDao.queryAll()
            _state.update { it.copy(loading = false, feeds = feeds) }
        } catch (e: Exception) {
            _state.update { it.copy(loading = false, message = e.localizedMessage) }
        }
    }

    fun load(num: Int) = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            _state.update {
                it.copy(
                    loading = false,
                    entries = Pager(
                        config = PagingConfig(50, enablePlaceholders = false),
                        remoteMediator = RssRemoteMediator(state.value.feeds[num], entryDao)
                    ) {
                        entryDao.queryByFeedId(state.value.feeds[num].id)
                    }.flow.cachedIn(viewModelScope)
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(loading = false, message = e.localizedMessage) }
        }
    }
}