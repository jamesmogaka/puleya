package com.example.puleya.data.model

import android.graphics.Bitmap

//
//The structure of a single track(song)
data class Track(

    // Unique ID
    val id: Int,
    val title: String,
    val artist: String?,

    // Drawable resource ID for album art
    val albumArt: Bitmap?,

    // Whether the track is in the favorites playlist
    val isFavorite: Boolean = false,

    //The total duration of the track in seconds
    val duration: Long,

    //Path to the actual track
    val path: String,

    //The id to the album under which the track belongs
    val albumId: Long?
)
