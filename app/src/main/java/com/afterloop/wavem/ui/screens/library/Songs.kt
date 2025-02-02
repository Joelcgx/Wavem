package com.afterloop.wavem.ui.screens.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import com.afterloop.wavem.R
import com.afterloop.wavem.data.model.Audio
import com.afterloop.wavem.ui.components.menu.PopupMenuComponent
import com.afterloop.wavem.viewmodel.audio.GetLocalAudio
import com.afterloop.wavem.viewmodel.player.WavemPlayerViewModel

@Composable
fun SongsContent(
    audioVM: GetLocalAudio,
    lazyListState: LazyListState,
    playerViewModel: WavemPlayerViewModel
) {
    // Obtiene la lista de canciones
    val audioList by audioVM.localAudioList.collectAsState()
    // Set the initial Playlist
    playerViewModel.setPlaylist(audioList)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState
    ) {
        items(audioList.size, key = { audioList[it].id }) { index ->
            SongItem(
                audio = audioList[index],
                audioVM = audioVM,
                playerViewModel = playerViewModel,
                index = index
            )
        }
    }
}

@Composable
private fun SongItem(
    audio: Audio,
    audioVM: GetLocalAudio,
    playerViewModel: WavemPlayerViewModel,
    index: Int
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) } // Estado para controlar la visibilidad del diálogo

    // Lista de opciones del menú
    val menuOptions = listOf(
        MenuOption.Play,
        MenuOption.Convert,
        MenuOption.Info
    )

    ListItem(
        modifier = Modifier.clickable(
            role = Role.Button,
            onClick = {
                playerViewModel.setIsPlaying(true)
                playerViewModel.setCurrentIndex(index)
                playerViewModel.setIsPlayBackActive(true)
            }
        ),
        leadingContent = {
            AsyncImage(
                modifier = Modifier.size(40.dp),
                model = ImageRequest.Builder(context)
                    .data(audio.albumArt)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_albumart_placeholder)
                    .build(),
                contentDescription = "Album Art",
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_albumart_placeholder),
                placeholder = painterResource(R.drawable.ic_albumart_placeholder)
            )
        },
        overlineContent = {},
        headlineContent = { Text(text = audio.title) },
        supportingContent = { Text(text = audio.artist) },
        trailingContent = {
            PopupMenuComponent(
                options = menuOptions,
                onOptionSelected = { option ->
                    when (option) {
                        MenuOption.Play -> {
                            // Reproducir la cancion
                            playerViewModel.setIsPlaying(true)
                            playerViewModel.setCurrentIndex(index)
                            playerViewModel.setIsPlayBackActive(true)
                        }

                        MenuOption.Convert -> {}

                        MenuOption.Info -> {
                            // Mostrar diálogo de información
                            showDialog = true
                        }
                    }
                },
                label = "Menú de opciones",
                width = 150.dp,
                height = 40.dp
            )
        }
    )
    InfoDialog(showDialog = showDialog, onShowDialog = { showDialog = false }, audio = audio)
}