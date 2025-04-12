package com.example.puleya.data.local.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.puleya.data.local.dao.TrackDao
import com.example.puleya.data.local.entity.TrackEntity

@Database(entities = [TrackEntity::class], version = 1)
abstract class PlayerDatabase: RoomDatabase() {
    abstract val trackDao: TrackDao
}