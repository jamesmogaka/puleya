package com.example.puleya.actions

//The possible events that the music player can omit to the view model
sealed class PlayerAction{

    //Toggle between play and pause
    object play : PlayerAction()

    //Go to the next track
    object next : PlayerAction()

    //Go to the previous track
    object previous : PlayerAction()

    //Minimize the player screen to a player component
    object minimize : PlayerAction()

    //Share the track
    object share : PlayerAction()

    //Get more Options or information about the track
    object more : PlayerAction()

    //Add or remove the track from the favorite playlist
    object favorite : PlayerAction()

    //Repeat the track
    object repeat : PlayerAction()

    //Play song randomly
    object shuffle : PlayerAction()

    //Set play timer
    data class timer(val time : Int) : PlayerAction()

    //Show the current playlist
    object playlist : PlayerAction()

    //Select output device
    object speaker : PlayerAction()
}