package me.yihtseu.jishi.repo.news

import androidx.paging.PagingSource
import androidx.room.*
import me.yihtseu.jishi.model.news.Entry

@Dao
interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entry: Entry)

    @Delete
    fun delete(entry: Entry)

    @Query("SELECT * FROM content WHERE feedId = :feedId")
    fun queryByFeedId(feedId: String): PagingSource<Int, Entry>
}