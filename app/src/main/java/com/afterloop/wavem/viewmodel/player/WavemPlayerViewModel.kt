package com.afterloop.wavem.viewmodel.player

import androidx.lifecycle.ViewModel
import com.afterloop.wavem.data.model.Audio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WavemPlayerViewModel @Inject constructor() : ViewModel() {
    // IF PLAYING
    private val _isPlaying = MutableStateFlow<Boolean>(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    fun setIsPlaying(isPlaying: Boolean) {
        _isPlaying.value = isPlaying
    }

    // CURRENT SONG and PLAYLIST
    private val _initPlaylist = MutableStateFlow(emptyList<Audio>())
    val initPlaylist: StateFlow<List<Audio>> = _initPlaylist.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    // Establece la lista de canciones
    fun setPlaylist(playlist: List<Audio>) {
        _initPlaylist.value = playlist
    }

    // Establece el índice actual
    fun setCurrentIndex(index: Int) {
        _currentIndex.value = index
    }


    // IF PLAYBACK IS ACTIVE
    private val _isPlayBackActive = MutableStateFlow<Boolean>(false)
    val isPlayBackActive: StateFlow<Boolean> = _isPlayBackActive.asStateFlow()

    fun setIsPlayBackActive(isPlayBackActive: Boolean) {
        _isPlayBackActive.value = isPlayBackActive
    }
}