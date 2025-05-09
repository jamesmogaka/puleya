package com.example.puleya.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.puleya.ui.view.components.PermissionScreen
import com.example.puleya.ui.view.screens.SplashScreen
import com.example.puleya.ui.viewmodel.TrackViewModel
import com.example.puleya.ui.view.screens.TrackList
import com.example.puleya.ui.viewmodel.PermissionViewModel
import com.example.puleya.ui.viewmodel.SplashScreenViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    //Create a navigation controller
    val navController = rememberNavController()

    //Create a navigation graph
    NavHost(navController = navController, startDestination = Splash, modifier = modifier) {

        //Add composable to the navigation graph
        // Splash Screen navigates to PermissionScreen
        composable<Splash> {
            //Create the splash screen view model
            val viewModel = hiltViewModel<SplashScreenViewModel>()
            SplashScreen(navigateToHome = {
                if (viewModel.arePermissionsGranted()) navController.navigate(TrackList)
                else navController.navigate(PermissionScreen)
            })
        }
        // Permission Screen navigates to TrackList only if permissions are granted
        composable<PermissionScreen> {
            //Create the permissions view model
            val viewModel = hiltViewModel<PermissionViewModel>()
            PermissionScreen(
                viewModel.requiredPermission,
                navigateToHome = { navController.navigate(TrackList) },
                onSettingsClick = { viewModel.openAppSettings()}
            )
        }
        composable<TrackList> {
            //Create the track list view model
            val viewModel = hiltViewModel<TrackViewModel>()
            //
            //Get the track list state
            val trackListState by viewModel.state.collectAsState()
            //Display the track list screen
            TrackList(tracksListState = trackListState, onAction = viewModel::onAction)
        }
        composable<Player> {  }
    }
}