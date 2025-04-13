package com.example.puleya.ui.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistPlay
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.example.puleya.R
import com.example.puleya.actions.PlayerAction
import com.example.puleya.data.model.PlayerState
import com.example.puleya.utils.TimeUtil


@Composable
fun Player(
    playerState: PlayerState,
    onAction: (PlayerAction) -> Unit
) {
    // **State for track progress**
    var progress by remember { mutableFloatStateOf(0.21f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // **Top Bar (Caret + Title)**
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp), // **Space between top bar & image**
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onAction(PlayerAction.minimize)}) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Minimize Player",
                    tint = Color.White
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "TRACK MIX",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    text = playerState.track.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = {}) { // **Invisible icon for symmetry**
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Color.Transparent)
            }
        }

        val painter = playerState.track.albumArt?.let { BitmapPainter(it.asImageBitmap()) } ?: painterResource(id = R.drawable.cover)
        Image(
            painter = painter,
            contentDescription = "Album Art",
            modifier = Modifier
                .fillMaxWidth(0.85f) // **Large image**
                .aspectRatio(1f) // **Maintains square shape**
                .weight(1.5f)
                .clip(RoundedCornerShape(8.dp))
        )

        // **Bottom Controls (Bundled in a Column)**
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 0.dp) // **Space above controls**
                .weight(1f), // **Pushes to bottom**
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // **Top Control Row (Share, More, Favorite)**
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { onAction(PlayerAction.share) }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                }
                IconButton(onClick = { onAction(PlayerAction.more) }, modifier = Modifier.border(1.dp, Color.White, CircleShape)) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More Options", tint = Color.White)
                }
                IconButton(onClick = { onAction(PlayerAction.favorite) }) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Favorite", tint = Color.White)
                }
            }

            // **Track Progress**
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "0:21", color = Color.Gray, fontSize = 12.sp)
                    Text(text = TimeUtil.formatTime(playerState.track.duration), color = Color.Gray, fontSize = 12.sp)
                }
                Slider(
                    value = progress,
                    onValueChange = { progress = it },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                        inactiveTrackColor = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // **Song Info**
            Column(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = playerState.track.title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = playerState.track.artist?:"", color = Color.Gray, fontSize = 14.sp)
            }

            // **Playback Controls**
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onAction(PlayerAction.repeat) }) {
                    Icon(imageVector = Icons.Filled.Repeat, contentDescription = "Repeat", tint = Color.White)
                }
               Row {
                   IconButton(onClick = { onAction(PlayerAction.previous) }) {
                       Icon(imageVector = Icons.Filled.SkipPrevious, contentDescription = "Previous", tint = Color.White)
                   }
                   IconButton(onClick = { onAction(PlayerAction.play) }) {
                       Icon(
                           imageVector = Icons.Filled.PlayArrow,
                           contentDescription = "Play",
                           tint = Color.White,
                           modifier = Modifier.size(48.dp)
                       )
                   }
                   IconButton(onClick = { onAction(PlayerAction.next) }) {
                       Icon(imageVector = Icons.Filled.SkipNext, contentDescription = "Next", tint = Color.White)
                   }
               }
                IconButton(onClick = { onAction(PlayerAction.shuffle) }) {
                    Icon(imageVector = Icons.Filled.Shuffle, contentDescription = "Shuffle", tint = Color.White)
                }
            }

            // **Bottom Row (Device, Timer, Menu)**
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onAction(PlayerAction.speaker) }) {
                    Icon(imageVector = Icons.Default.Speaker, contentDescription = "Device Selection", tint = Color.White)
                }
                IconButton(onClick = { /* TODO: Timer */ }) {
                    Icon(imageVector = Icons.Default.Timer, contentDescription = "Timer", tint = Color.White)
                }
                IconButton(onClick = { onAction(PlayerAction.playlist) }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.PlaylistPlay, contentDescription = "Menu", tint = Color.White)
                }
            }
        }
    }
}

