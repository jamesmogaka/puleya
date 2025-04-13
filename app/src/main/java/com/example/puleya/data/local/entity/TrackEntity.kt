package com.example.puleya.data.local.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "track",
    indices = [Index (
        value = ["title", "artist"],
        unique = true,
        name = "id"
    )]
)
data class TrackEntity(
    val title: String,
    val artist: String,
    val duration: Long,
    @ColumnInfo(name = "file_path") val path: String,
    @ColumnInfo(name = "album")val albumId: Long,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "track") val id: Int = 0,
)
