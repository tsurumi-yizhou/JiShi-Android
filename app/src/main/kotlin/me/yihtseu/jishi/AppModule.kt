package me.yihtseu.jishi

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.yihtseu.jishi.repo.news.EntryDao
import me.yihtseu.jishi.repo.news.FeedDao
import me.yihtseu.jishi.repo.news.NewsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room
            .databaseBuilder(context, NewsDatabase::class.java, "news")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideEntryDao(database: NewsDatabase): EntryDao {
        return database.entryDao()
    }

    @Provides
    @Singleton
    fun provideFeedDao(database: NewsDatabase): FeedDao {
        return database.feedDao()
    }
}