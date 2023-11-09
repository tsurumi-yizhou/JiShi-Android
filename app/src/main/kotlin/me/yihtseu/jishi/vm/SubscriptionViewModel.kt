package me.yihtseu.jishi.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huawei.hms.push.HmsMessaging
import com.prof18.rssparser.RssParserBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.news.Feed
import me.yihtseu.jishi.repo.news.FeedDao
import me.yihtseu.jishi.utils.crypto.md5
import javax.inject.Inject

data class SubscriptionState(
    val loading: Boolean = false,
    val message: String? = null,
    val feeds: List<Feed> = emptyList()
)

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val feedDao: FeedDao
) : ViewModel() {
    private val parser = RssParserBuilder(
        charset = Charsets.UTF_8
    ).build()

    private val _state = MutableStateFlow(SubscriptionState())
    val state = _state.asStateFlow()
    fun init() = viewModelScope.launch {
        _state.update { it.copy(feeds = feedDao.queryAll()) }
    }

    fun add(link: String) = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val channel = parser.getRssChannel(link)
            val feed =
                Feed(
                    title = channel.title.orEmpty(),
                    subtitle = channel.description.orEmpty(),
                    link = link,
                    id = link,
                    updated = System.currentTimeMillis()
                )
            HmsMessaging.getInstance(context).subscribe(md5(feed.id))
                .addOnSuccessListener {
                    feedDao.insert(feed)
                    _state.update { it.copy(loading = false, feeds = feedDao.queryAll()) }
                }
                .addOnFailureListener {
                    _state.update { it.copy(loading = false, message = it.message) }
                }

        } catch (e: Exception) {
            _state.update { it.copy(loading = false, message = e.localizedMessage) }
        }
    }

    fun sub(feed: Feed) = viewModelScope.launch {
        feedDao.delete(feed)
        _state.update { it.copy(feeds = feedDao.queryAll()) }
    }
}