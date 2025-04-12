package com.example.puleya.data.model

sealed class PermissionUiState{
    object Granted: PermissionUiState()
    object Denied: PermissionUiState()
    object Loading: PermissionUiState()
}