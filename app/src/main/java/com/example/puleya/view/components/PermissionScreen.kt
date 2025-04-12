package com.example.puleya.view.components

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun  PermissionScreen(requiredPermission:String, navigateToHome: () -> Unit, onSettingsClick: () -> Unit) {

    val permissionState = rememberPermissionState(requiredPermission)

    Scaffold {
        when {
            permissionState.status.isGranted -> navigateToHome()
            else -> {
                LaunchedEffect(Unit) {
                    permissionState.launchPermissionRequest()
                }
                PermissionRationale(
                    onSettingsClick = onSettingsClick
                )
            }
        }
    }
}
