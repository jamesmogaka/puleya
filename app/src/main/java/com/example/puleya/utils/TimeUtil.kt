package com.example.puleya.utils

import java.util.concurrent.TimeUnit

object TimeUtil {
    fun formatTime(milliSeconds: Long): String {
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliSeconds)
        val minutes = TimeUnit.SECONDS.toMinutes(seconds)
        val remainingSeconds = seconds % 60
        return String.format("%d:%02d", minutes, remainingSeconds)
    }
}