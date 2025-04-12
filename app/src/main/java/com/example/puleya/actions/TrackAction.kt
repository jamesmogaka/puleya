package com.example.puleya.actions

//This are the actions that are possible from the track list
sealed class TrackAction {
    object play:TrackAction()
    object moreInfo:TrackAction()
    object like:TrackAction()
}