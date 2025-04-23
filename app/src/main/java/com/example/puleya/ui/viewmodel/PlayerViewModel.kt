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
        //Create a token pointing to the media session running in the playback service
        val sessionToken = SessionToken(this.appContext, ComponentName(this.appContext, MusicPlaybackService::class.java))

        //Start building the media controller that will connect tho the media session using the token
        val controllerFuture = MediaController.Builder(this.appContext, sessionToken).buildAsync()

        //Add a listener to run when the controller is fully connected
        controllerFuture.addListener(
            {
                //Get the ready controller
                mediaController= controllerFuture.get()
                //Register a listener to get notified of playback events
                mediaController?.addListener(playerListener)
                //Refresh the current state with the latest values from the media controller
                updateState()
            },
            MoreExecutors.directExecutor()
        )
    }
    //
    //Update the state with the latest values retrieved from the media controller
    private fun updateState() {
        mediaController?.let { controller ->
            _state.value = _state.value.copy(
                isPlaying = controller.isPlaying
                //TODO:Update the entire player state
            )
        }
    }
    // Listener for player events
    private val playerListener = object : Player.Listener {
        //Toggle the playback state to indicate whether the player is playing or paused
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _state.value = _state.value.copy(isPlaying = isPlaying)
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            _state.value = _state.value.copy(
                //
                //TODO:Update the track with the new metadata
            )
        }
        // Override other methods as needed (e.g., onTimelineChanged, onPlayerError)
        override fun onEvents(player: Player, events: Player.Events) {
            // Called for multiple events at once - update everything
            if (
                events.containsAny(
                    Player.EVENT_PLAYBACK_STATE_CHANGED,
                    Player.EVENT_MEDIA_METADATA_CHANGED,
                    Player.EVENT_IS_PLAYING_CHANGED
                )
            ) {
                updateState()
            }
        }
    }
}