package com.example.puleya.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.puleya.data.local.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    //Insert or update a track
    @Upsert
    suspend fun upsert(track: TrackEntity)
    //Retrieve all cached tracks
    @Query("SELECT * FROM track ORDER BY title ASC")
    fun getAllTracks(): Flow<List<TrackEntity>>
    //Retrieve the number of cached tracks
    @Query("SELECT COUNT(*) FROM track")
    suspend fun getCount(): Int
}