package com.example.puleya.di

import android.content.Context
import androidx.room.Room
import com.example.puleya.data.local.dao.TrackDao
import com.example.puleya.data.local.database.PlayerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    //
    @Provides
    @Singleton
    fun providePlayerDatabase(
        @ApplicationContext context: Context
    ): PlayerDatabase {
        return Room.databaseBuilder(
            context,
            PlayerDatabase::class.java,
            "player.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrackDao(
        db:PlayerDatabase
    ): TrackDao{
        return db.trackDao;
    }
}