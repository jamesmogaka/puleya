package com.example.puleya.data.model

//The information needed for the player screen to work
data class PlayerState(

    //The track that is currently playing
    val track: Track,

    //The progress of the current track
    val progress:Int,
)
