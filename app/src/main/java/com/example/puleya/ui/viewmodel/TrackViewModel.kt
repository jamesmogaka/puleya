package com.example.puleya.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puleya.actions.TrackAction
import com.example.puleya.data.model.TrackListState
import com.example.puleya.data.repository.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(
    //TODO:Use an abstraction(interface rather that the actual repository)
    private val trackRepository: TrackRepository
): ViewModel() {
    //Encapsulate the state of the view model
    private val _state = MutableStateFlow(TrackListState(tracks = emptyList(), query = ""))
    val state = _state.asStateFlow()

    //Initialize the state(Particularly the track list )
    init {
        //Get the tracks
       viewModelScope.launch {
           // Start with loading state if needed
           _state.value = _state.value.copy(isLoading = true)

           // Collect from the Flow instead of directly assigning tracks
           trackRepository.getMusicFiles()
               .catch { e ->
                   // Handle any errors from the repository
                   _state.value = _state.value.copy(
                       isLoading = false,
                       error = e.message ?: "Unknown error occurred"
                   )
               }
               .collect { tracks ->
                   // Update state with new tracks
                   _state.value = _state.value.copy(
                       tracks = tracks,
                       isLoading = false
                   )
               }
       }
    }
    //This is some sort of event listener for the music list page that calls relevant methods based on the action
    fun onAction(action: TrackAction){

    }
}