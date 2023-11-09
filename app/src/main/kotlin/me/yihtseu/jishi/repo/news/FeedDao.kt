package me.yihtseu.jishi.repo.news

import androidx.room.*
import me.yihtseu.jishi.model.news.Feed

@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(feed: Feed)

    @Delete
    fun delete(feed: Feed)

    @Query("SELECT * FROM feeds WHERE id = :id")
    fun queryById(id: String): Feed

    @Query("SELECT * FROM feeds")
    fun queryAll(): List<Feed>
}