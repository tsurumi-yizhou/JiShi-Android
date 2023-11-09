package me.yihtseu.jishi.repo.news

import androidx.room.Database
import androidx.room.RoomDatabase
import me.yihtseu.jishi.model.news.Entry
import me.yihtseu.jishi.model.news.Feed

@Database(entities = [Entry::class, Feed::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao

    abstract fun entryDao(): EntryDao
}