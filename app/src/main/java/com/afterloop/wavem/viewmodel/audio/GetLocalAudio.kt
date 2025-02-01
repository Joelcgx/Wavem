package com.afterloop.wavem.viewmodel.audio

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afterloop.wavem.data.model.Audio
import com.afterloop.wavem.data.repository.audio.LocalAudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetLocalAudio @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _localAudioList = MutableStateFlow<List<Audio>>(emptyList<Audio>())
    val localAudioList = _localAudioList

    fun getLocalAudioFromStorage(): List<Audio> {
        viewModelScope.launch(Dispatchers.IO) {
            _localAudioList.value = LocalAudioRepository(context).queryLocalAudioFiles()
        }
        return _localAudioList.value
    }
}