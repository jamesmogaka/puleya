package com.example.puleya.ui.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.puleya.actions.TrackAction
import com.example.puleya.data.model.TrackListState
import com.example.puleya.ui.view.components.TrackItem

@Composable
fun TrackList(tracksListState: TrackListState, onAction: (TrackAction) -> Unit) {
    // Snack bar host for showing error toasts
    val snackBarHostState = remember { SnackbarHostState() }
    // Show error toast if error message exists
    LaunchedEffect(tracksListState.error) {
        tracksListState.error?.let { message ->
            snackBarHostState.showSnackbar(message)
        }
    }
    // Main scaffold layout with snack bar
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            //TODO: Add a search bar

            // Spacing below SearchBar
            Spacer(modifier = Modifier.height(8.dp))

            when {
                tracksListState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                else -> {
                    // Displays a scrollable list of track items
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        // Iterates over the tracks and creates a TrackItem for each track
                        items(tracksListState.tracks) { track ->
                            TrackItem(
                                track = track,
                                onAction
                            )
                        }
                    }
                }
            }
        }
    }
}
