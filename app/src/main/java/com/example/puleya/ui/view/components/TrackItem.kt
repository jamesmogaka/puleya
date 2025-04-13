package com.example.puleya.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.puleya.R
import com.example.puleya.actions.TrackAction
import com.example.puleya.data.model.Track
import com.example.puleya.ui.theme.DeepPurple

@Composable
fun TrackItem(
    track: Track,
    onAction: (TrackAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onAction(TrackAction.play) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Album Art
        val painter = track.albumArt?.let { BitmapPainter(it.asImageBitmap()) } ?: painterResource(id = R.drawable.cover)
        Image(
            painter = painter,
            contentDescription = "Album Art",
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Track Info (Title & Artist)
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = track.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = track.artist ?:"",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Favorite Icon
        IconButton(onClick = { onAction(TrackAction.like) }) {
            Icon(
                imageVector = if (track.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite Icon",
                tint = if (track.isFavorite) DeepPurple else MaterialTheme.colorScheme.onSurface,
            )
        }

        // More Options (⋮) Icon
        IconButton(onClick = { onAction(TrackAction.moreInfo) }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Options",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}


