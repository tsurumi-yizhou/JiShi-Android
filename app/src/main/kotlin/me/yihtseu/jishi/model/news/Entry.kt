package me.yihtseu.jishi.model.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content")
data class Entry(
    @PrimaryKey val id: String,
    @ColumnInfo val feedId: String,
    @ColumnInfo val title: String,
    @ColumnInfo val abstract: String,
    @ColumnInfo val image: String?,
    @ColumnInfo val updated: String,
    @ColumnInfo val link: String,
    @ColumnInfo val content: String
)
