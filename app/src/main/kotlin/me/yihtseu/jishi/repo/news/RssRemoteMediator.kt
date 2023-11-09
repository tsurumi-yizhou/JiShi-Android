@file:OptIn(ExperimentalPagingApi::class)

package me.yihtseu.jishi.repo.news

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.prof18.rssparser.RssParserBuilder
import me.yihtseu.jishi.model.news.Entry
import me.yihtseu.jishi.model.news.Feed
import kotlin.math.abs

class RssRemoteMediator(
    private val feed: Feed,
    private val entryDao: EntryDao
) : RemoteMediator<Int, Entry>() {

    private val parser = RssParserBuilder(charset = Charsets.UTF_8).build()
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, Entry>
    ): MediatorResult {
        try {
            parser.getRssChannel(feed.link).items.forEach {
                entryDao.insert(
                    Entry(
                        id = it.guid!!,
                        feedId = feed.id,
                        title = it.title!!,
                        abstract = it.description.orEmpty(),
                        content = it.content.orEmpty()
                    )
                )
            }
            return MediatorResult.Success(true)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return if (abs(feed.updated - System.currentTimeMillis()) > 5 * 60 * 1000)
            InitializeAction.LAUNCH_INITIAL_REFRESH
        else
            InitializeAction.SKIP_INITIAL_REFRESH
    }
}