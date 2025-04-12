package com.example.puleya.data.repository

import android.app.Application
import android.content.ContentUris
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import com.example.puleya.data.local.dao.TrackDao
import com.example.puleya.data.model.Track
import javax.inject.Inject


class TrackRepository @Inject constructor(
    private val appContext: Application,
    private val trackDao: TrackDao
) {
    //Read all the music files from the device
    fun getMusicFiles():List<Track>{
        //Get the content resolver
        val contentResolver = appContext.contentResolver
        //Query the android media store to get all audio files. The cursor returned allows for
        // iteration to retrieve the results
        val cursor: Cursor? = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            //The columns to get for each audio track
            arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                // Track duration (milliseconds)
                MediaStore.Audio.Media.DURATION,
                // File path (where the track is stored)
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID
            ),
            //Filter to only get music files
            "${MediaStore.Audio.Media.IS_MUSIC} != 0",
            //Selection arguments
            null,
            MediaStore.Audio.Media.TITLE + " ASC"
        )
        //Initialize an empty track list
        val tracks = mutableListOf<Track>()
        //Iterate through the cursor to get each track
        //Use ensure that the cursor is safely closed after use
        cursor?.use { getTracks(it, tracks)}
        return  tracks
    }
    //Get the tracks given a cursor
    private fun getTracks(cursor: Cursor, tracks: MutableList<Track>){
        //Retrieve the column ids which we can use to get the data
        val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
        val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
        val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
        val pathColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
        val albumIdColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
        //Go through each row of the cursor and extract the relevant details
        while (cursor.moveToNext()) {
            //The album id is used to get the album art
            val albumId = cursor.getLong(albumIdColumn)
            // Add a new Track object to the list
            tracks.add(
                Track(
                    cursor.getInt(idColumn),
                    cursor.getString(titleColumn),
                    cursor.getString(artistColumn),
                    this.getArt(albumId),
                    false,
                    cursor.getLong(durationColumn),
                    cursor.getString(pathColumn),
                    albumId
                )
            )
        }
    }

    // Function to get album art as an Android resource ID
    private fun getArt(albumId: Long?): Bitmap? {
        // Return nothing if no album id was given
        if (albumId == null) return null
        // Construct the album art URI
        val uri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId)
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // For Android 10 and above (API 29+)
                val size = Size(200, 200)
                this.appContext.contentResolver.loadThumbnail(uri, size, null)
            } else {
                // For older Android versions
                val parcelFileDescriptor = this.appContext.contentResolver.openFileDescriptor(uri, "r")
                val fileDescriptor = parcelFileDescriptor?.fileDescriptor
                val bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                parcelFileDescriptor?.close()
                bitmap
            }
        } catch (e: Exception) {
            // If thumbnail retrieval fails, Do nothing
            return null
        }
    }

    //
}