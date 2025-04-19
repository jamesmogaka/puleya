package com.example.puleya.data.model

//The information needed for the player screen to work
data class PlayerState(

    //The track that is currently playing
    val track: Track,

    //The progress of the current track
    val progress:Int,

    //Indication if the track is playing or paused
    val isPlaying: Boolean = false,

    //The playlist
    val playlist: List<Track> = emptyList(),

    //The current index of the track in the playlist
    val currentTrackIndex: Int = 0
)
