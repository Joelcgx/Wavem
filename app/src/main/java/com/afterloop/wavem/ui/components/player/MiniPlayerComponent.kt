package com.afterloop.wavem.ui.components.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.afterloop.wavem.R
import com.afterloop.wavem.viewmodel.player.WavemPlayerViewModel

@Composable
fun MiniPlayer(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val playerVM = hiltViewModel<WavemPlayerViewModel>()
    val isPlaying by playerVM.isPlaying.collectAsState(false)
    val currentIndex by playerVM.currentIndex.collectAsState(initial = 0)
    val playlist by playerVM.initPlaylist.collectAsState(initial = emptyList())
    val isPlayBackActive by playerVM.isPlayBackActive.collectAsState(initial = false)

    AnimatedVisibility(
        visible = isPlaying,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            // Divider en la parte superior
            HorizontalDivider(color = MaterialTheme.colorScheme.primary.copy(0.2f))
            // Contenido del reproductor
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Imagen y información
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f) // Ocupa el espacio disponible
                ) {
                    AsyncImage(
                        modifier = Modifier.size(40.dp),
                        model = ImageRequest.Builder(context)
                            .data(playlist[currentIndex].albumArt)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Album Art",
                        error = painterResource(R.drawable.ic_albumart_placeholder)
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = playlist[currentIndex].title,
                            maxLines = 1,
                            modifier = Modifier.basicMarquee(Int.MAX_VALUE),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = playlist[currentIndex].artist, maxLines = 1,
                            modifier = Modifier.basicMarquee(Int.MAX_VALUE),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // Controles de reproducción
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    // Botón de retroceso
                    IconButton(onClick = { /* Acción para retroceder */ }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_previous),
                            contentDescription = "Previous"
                        )
                    }

                    // Botón de play/pause
                    IconButton(onClick = { playerVM.setIsPlayBackActive(!isPlayBackActive) }) {
                        Icon(
                            painter = if (isPlayBackActive) painterResource(R.drawable.ic_pause) else painterResource(
                                R.drawable.ic_play
                            ),
                            contentDescription = if (isPlaying) "Pause" else "Play"
                        )
                    }

                    // Botón de avance
                    IconButton(onClick = { /* Acción para avanzar */ }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_next),
                            contentDescription = "Next"
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom
            ) {
                // ProgressBar en la parte inferior sin padding
                LinearProgressIndicator(
                    progress = { 0.5f },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}