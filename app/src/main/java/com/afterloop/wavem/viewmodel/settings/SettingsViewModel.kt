package com.afterloop.wavem.viewmodel.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afterloop.wavem.WavemApplication
import com.afterloop.wavem.utils.LocaleUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val dataStore: DataStore<Preferences> =
        (context.applicationContext as WavemApplication).dataStore

    companion object {
        private val PREFS_LANG_KEY = stringPreferencesKey("app_language")
        private val PREFS_AUDIO_LOW_LATENCY_KEY = booleanPreferencesKey("audio_low_latency")
        private val PREFS_PCM_OUTPUT_KEY = stringPreferencesKey("pcm_output")
        private val PREFS_AUDIO_POWER_SAVING_MODE_KEY =
            booleanPreferencesKey("audio_power_saving_mode")
    }

    // LANGUAGE
    val selectedLang: StateFlow<String> = dataStore.data
        .map { it[PREFS_LANG_KEY] ?: "en" }
        .stateIn(viewModelScope, SharingStarted.Lazily, "en")

    fun setSelectedLang(lang: String) {
        viewModelScope.launch {
            dataStore.edit { it[PREFS_LANG_KEY] = lang }
            LocaleUtils.applyLocale(context, lang)
            _requestRestart.value = true
        }
    }

    // AUDIO
    val selectedAudioLowLatency: StateFlow<Boolean> = dataStore.data
        .map { it[PREFS_AUDIO_LOW_LATENCY_KEY] ?: false }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val selectedAudioPowerSavingMode: StateFlow<Boolean> = dataStore.data
        .map { it[PREFS_AUDIO_POWER_SAVING_MODE_KEY] ?: false }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setAudioLowLatency(lowLatency: Boolean) {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[PREFS_AUDIO_LOW_LATENCY_KEY] = lowLatency
                if (lowLatency) {
                    prefs[PREFS_AUDIO_POWER_SAVING_MODE_KEY] = false
                }
            }
        }
    }

    fun setAudioPowerSavingMode(powerSavingMode: Boolean) {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[PREFS_AUDIO_POWER_SAVING_MODE_KEY] = powerSavingMode
                if (powerSavingMode) {
                    prefs[PREFS_AUDIO_LOW_LATENCY_KEY] = false
                }
            }
        }
    }

    // PCM
    val selectedPcmOutput: StateFlow<String> = dataStore.data
        .map { it[PREFS_PCM_OUTPUT_KEY] ?: "16" }
        .stateIn(viewModelScope, SharingStarted.Lazily, "16")

    fun setPcmOutput(output: String) {
        viewModelScope.launch {
            dataStore.edit { it[PREFS_PCM_OUTPUT_KEY] = output }
        }
    }

    // RESTART
    private val _requestRestart = MutableStateFlow(false)
    val requestRestart: StateFlow<Boolean> = _requestRestart.asStateFlow()
    fun setRequestRestart(restart: Boolean) {
        _requestRestart.value = restart
    }
}