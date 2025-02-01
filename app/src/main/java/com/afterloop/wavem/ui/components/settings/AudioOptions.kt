package com.afterloop.wavem.ui.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.afterloop.wavem.R

data class AudioOptions(
    val title: String,
    val description: String,
    val isSwitch: Boolean,
    val isDropMenu: Boolean
) {
    companion object {
        @Composable
        fun getAudioOptions(): List<AudioOptions> = listOf(
            AudioOptions(
                title = stringResource(R.string.audio_low_latency_label),
                description = stringResource(R.string.audio_low_latency_description),
                isSwitch = true,
                isDropMenu = false
            ),
            AudioOptions(
                title = stringResource(R.string.audio_pcm_output_label),
                description = stringResource(R.string.audio_pcm_output_label),
                isSwitch = false,
                isDropMenu = true
            )
        )
    }
}
