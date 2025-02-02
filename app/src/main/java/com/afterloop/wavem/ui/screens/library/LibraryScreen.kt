package com.afterloop.wavem.ui.screens.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.afterloop.wavem.R
import com.afterloop.wavem.viewmodel.audio.GetLocalAudio
import com.afterloop.wavem.viewmodel.player.WavemPlayerViewModel

@Composable
fun LibraryScreen(audioVM: GetLocalAudio, playerViewModel: WavemPlayerViewModel) {
    LaunchedEffect(Unit) {
        // Load local audio
        audioVM.getLocalAudioFromStorage()
    }

    val tabs = listOf(
        stringResource(R.string.library_tab_songs),
        stringResource(R.string.library_tab_artist),
        stringResource(R.string.library_tab_albums),
        stringResource(R.string.library_tab_playlist)
    )
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    val songListState = rememberLazyListState()

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex, indicator = { tabPositions ->
                SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = MaterialTheme.colorScheme.primary,
                    height = 2.dp
                )
            }) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1
                        )
                    })
            }
        }

        when (selectedTabIndex) {
            0 -> SongsContent(audioVM, songListState, playerViewModel)
        }
    }
}