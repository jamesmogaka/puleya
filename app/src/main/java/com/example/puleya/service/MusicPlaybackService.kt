package com.example.puleya.service

import android.content.Intent
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

//
class MusicPlaybackService:MediaSessionService() {
    //Hold the core components
    private var mediaSession:MediaSession? = null
    private lateinit var player: ExoPlayer
    //Initialize the player and media session when this service is started
    override fun onCreate() {
        super.onCreate()
        //Initialize the exoplayer and customize it to better handle playing of media tracks
        player = ExoPlayer.Builder(this)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                true
            )
            .build()
        //Initialize the media session
        //Think of Having a custom Media controls
        //TO either like or dislike tracks, add tracks to a playlist, save track offline, change audio quality
        mediaSession = MediaSession.Builder(this, player).build()
        //
        //Load the initial playlist and also setup custom notification
    }
    //To link between the service and the controllers. This method is called whenever a controller connects to this service
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }
    //Release resources to prevent memory leaks
    override fun onDestroy() {
        //Release resources
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
    //
    //Stop the media service if the user removes the app from the recent and no music is currently playing
    override fun onTaskRemoved(rootIntent: Intent?) {
        // Check if the player is NOT supposed to be playing and stop the media session service
        if (!player.playWhenReady) stopSelf()
    }
}