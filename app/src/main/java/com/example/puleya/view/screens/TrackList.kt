package com.example.puleya.view.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.puleya.actions.TrackAction
import com.example.puleya.data.model.TrackListState
import com.example.puleya.view.components.TrackItem

@Composable
fun TrackList(tracksListState: TrackListState, onAction: (TrackAction) -> Unit) {

    // Displays a scrollable list of track items
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        //TODO: Add a search bar

        // Iterates over the tracks and creates a TrackItem for each track
        items(tracksListState.tracks) {
            track ->
                TrackItem(
                   track = track,
                    onAction
                )
        }
    }
}
