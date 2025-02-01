package com.afterloop.wavem.viewmodel.audio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.afterloop.wavem.data.model.AudioInfoModel
import com.arthenica.ffmpegkit.FFprobeKit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AudioInfoVM @Inject constructor() : ViewModel() {
    // Estado para los detalles del audio
    var audioInfo by mutableStateOf<AudioInfoModel?>(null)
        private set
    private val _audioInfo = MutableStateFlow<AudioInfoModel?>(null)

    val audioInfoFlow: MutableStateFlow<AudioInfoModel?> = _audioInfo

    fun loadAudioDetails(audioPath: String) {
        FFprobeKit.getMediaInformationAsync(audioPath) {
            val stream = it.mediaInformation.allProperties.getJSONArray("streams").getJSONObject(0)

            val bitrate = it.mediaInformation.bitrate.toDouble()
            val bitrateKbps = bitrate / 1000
            val formattedBitrate = String.format(Locale.US, "%.0f", bitrateKbps)

            var channels = it.mediaInformation.streams[0].channelLayout
            val sampleRate = it.mediaInformation.streams[0].sampleRate.toInt()
            val format = it.mediaInformation.format
            var bitsPerSample = stream?.optString("bits_per_raw_sample")
            if (bitsPerSample == "") {
                bitsPerSample = stream?.optString("sample_fmt")
            }
            val size = bytesToMegaBytes(it.mediaInformation.size.toLong())
            val durationSeconds = it.mediaInformation.duration.toDouble()
            val duration = secondsToMinutes(durationSeconds.toLong())
            val tags =
                it.mediaInformation.allProperties.getJSONObject("format").optJSONObject("tags")

            var date = tags?.optString("DATE", "Unknown") ?: "Unknown"
            val discNumber = tags?.optString("disc", "1") ?: "1"
            val trackNumber = tags?.optString("track", "1") ?: "1"
            var artist = tags?.optString("ARTIST", "Unknown") ?: "Unknown"
            var album = tags?.optString("ALBUM", "Unknown") ?: "Unknown"
            var genre = tags?.optString("GENRE", "Unknown") ?: "Unknown"
            var composer = tags?.optString("COMPOSER", "Unknown") ?: "Unknown"
            var copyRight = tags?.optString("COPYRIGHT", "Unknown") ?: "Unknown"

            if (channels == null || format == "wav") {
                channels =
                    it.mediaInformation.allProperties.getJSONArray("streams").getJSONObject(0)
                        .getString("channel_layout")
            }
            if (album == "Unknown") {
                album = tags?.optString("album", "Unknown") ?: "Unknown"
            }
            if (artist == "Unknown") {
                artist = tags?.optString("artist", "Unknown") ?: "Unknown"
            }
            if (genre == "Unknown") {
                genre = tags?.optString("genre", "Unknown") ?: "Unknown"
            }
            if (composer == "Unknown") {
                composer = tags?.optString("composer", "Unknown") ?: "Unknown"
            }
            if (date == "Unknown") {
                date = tags?.optString("date", "Unknown") ?: "Unknown"
            }
            audioInfo = AudioInfoModel(
                formattedBitrate,
                channels,
                sampleRate,
                format,
                bitsPerSample!!,
                size.toString(),
                duration,
                date,
                discNumber,
                trackNumber,
                artist,
                album,
                genre,
                composer
            )
            _audioInfo.value = audioInfo
        }
    }
}

private fun bytesToMegaBytes(bytes: Long): String {
    val sizeInMB = bytes / (1024.0 * 1024.0)
    return String.format(Locale.US, "%.2f", sizeInMB)
}

private fun secondsToMinutes(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format(Locale.US, "%02d:%02d", minutes, remainingSeconds)
}