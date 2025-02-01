package com.afterloop.wavem.ui.screens.library

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import com.afterloop.wavem.R
import com.afterloop.wavem.data.model.Audio
import com.afterloop.wavem.ui.components.menu.PopupMenuComponent
import com.afterloop.wavem.viewmodel.audio.GetLocalAudio

@Composable
fun SongsContent(audioVM: GetLocalAudio, lazyListState: LazyListState) {
    val audioList by audioVM.localAudioList.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState
    ) {
        items(audioList.size, key = { audioList[it].id }) { index ->
            SongItem(audio = audioList[index])
        }
    }
}

@Composable
private fun SongItem(audio: Audio) {
    val context = LocalContext.current
    val menuOptions = listOf(
        OptionsModel(stringResource(R.string.library_options_play), R.drawable.ic_play_filled),
        OptionsModel(stringResource(R.string.library_options_convert), R.drawable.ic_audio),
        OptionsModel(stringResource(R.string.library_option_info), R.drawable.ic_info)
    )

    ListItem(
        leadingContent = {
            AsyncImage(
                modifier = Modifier.size(40.dp),
                model = ImageRequest.Builder(context).data(audio.albumArt).crossfade(true)
                    .placeholder(R.drawable.ic_albumart_placeholder).build(),
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
                    // Manejar selección aquí
                    when (option.title) {
                    }
                },
                label = "Menú de opciones",
                width = 150.dp,
                height = 40.dp
            )
        }
    )
}