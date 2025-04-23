package com.example.puleya.ui.viewmodel

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.puleya.actions.TrackAction
import com.example.puleya.data.model.PlayerState
import com.example.puleya.service.MusicPlaybackService
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val appContext: Application
):ViewModel() {
    //Encapsulate the state of the player screen
    private val _state = MutableStateFlow(PlayerState())
    val state = _state.asStateFlow()

    //THe media controller to control the player
    private var mediaController: MediaController? = null
    init {
        //Start the background playback service
        Intent(appContext, MusicPlaybackService::class.java).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                appContext.startForegroundService(it)
            }
        }
        //Connect to the media session
        connectToMediaSession()
        //Start tracking playback position
    }
    //Handle the user interactions based to the specific actions that the user did
    fun onAction(action: TrackAction){
    }

    //Setup the media controller by connecting to the session and creating a media controller to control
    //it. The media controller allows us to control music playback form the background service
    fun connectToMediaSession(){

    }

}