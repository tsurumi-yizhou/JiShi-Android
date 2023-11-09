package me.yihtseu.jishi.model.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feeds")
data class Feed(
    @PrimaryKey val id: String,
    @ColumnInfo val title: String,
    @ColumnInfo val subtitle: String,
    @ColumnInfo val updated: Long,
    @ColumnInfo val link: String
)