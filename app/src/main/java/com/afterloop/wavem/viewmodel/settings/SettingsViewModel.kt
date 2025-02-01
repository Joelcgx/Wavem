package com.afterloop.wavem.viewmodel.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afterloop.wavem.utils.LocaleUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @ApplicationContext private val context: Context
) : ViewModel() {
    companion object {
        private const val PREFS_LANG_KEY = "app_language"
        private const val PREFS_AUDIO_LOW_LATENCY_KEY = "audio_low_latency"
        private const val PREFS_PCM_OUTPUT_KEY = "pcm_output"
    }

    // TODO: LANGUAGE
    private val _selectedLang = MutableStateFlow("en")
    val selectedLang: MutableStateFlow<String> = _selectedLang

    init {
        viewModelScope.launch {
            _selectedLang.value = getSavedLanguage()
        }
    }

    private fun getSavedLanguage(): String {
        return sharedPreferences.getString(PREFS_LANG_KEY, "en") ?: "en"
    }

    fun setSelectedLang(lang: String, onSuccess: () -> Unit) {
        _selectedLang.value = lang
        viewModelScope.launch {
            sharedPreferences.edit().putString(PREFS_LANG_KEY, lang).apply()
            LocaleUtils.saveLanguage(context, lang) // Guardar en SharedPreferences
            LocaleUtils.applyLocale(context, lang)   // Aplicar cambios de idioma
            onSuccess()                              // Reiniciar la app
        }
    }

    private val _requestRestart = MutableStateFlow(false)
    val requestRestart: MutableStateFlow<Boolean> = _requestRestart
    fun setRequestRestart(restart: Boolean) {
        _requestRestart.value = restart
    }

    // TODO: AUDIO
    // Low Latency
    private val _selectedAudioLowLatency = MutableStateFlow(
        sharedPreferences.getBoolean(
            PREFS_AUDIO_LOW_LATENCY_KEY,
            false
        )
    )
    val selectedAudioLowLatency: MutableStateFlow<Boolean> = _selectedAudioLowLatency

    fun setAudioLowLatency(lowLatency: Boolean) {
        _selectedAudioLowLatency.value = lowLatency
        viewModelScope.launch {
            sharedPreferences.edit()
                .putBoolean(PREFS_AUDIO_LOW_LATENCY_KEY, lowLatency)
                .apply()
        }
    }

    // PCM
    private val _selectedPcmOutput = MutableStateFlow(
        sharedPreferences.getString(
            PREFS_PCM_OUTPUT_KEY,
            "16"
        ) ?: "16"
    )
    val selectedPcmOutput: MutableStateFlow<String> = _selectedPcmOutput

    fun setPcmOutput(output: String) {
        _selectedPcmOutput.value = output
        viewModelScope.launch {
            sharedPreferences.edit()
                .putString(PREFS_PCM_OUTPUT_KEY, output)
                .apply()
        }
    }
}