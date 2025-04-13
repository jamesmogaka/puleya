package com.example.puleya.data.model

data class TrackListState(
    //The collection of tracks
    val tracks: List<Track>,
    //TODO: The search query
    val query:String,
    //indication of loading state
    val isLoading: Boolean = false,
    //Indication of error state
    val error: String? = null
)
