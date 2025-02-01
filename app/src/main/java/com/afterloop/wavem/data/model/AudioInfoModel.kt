package com.afterloop.wavem.data.model

/**
 * Data class representing the audio information of a track.
 *
 * @property bitrate the bitrate of the audio track
 * @property channels the number of channels in the audio track
 * @property sampleRate the sample rate of the audio track
 * @property format the audio format of the track
 * @property bitsPerSample the number of bits per sample
 * @property size the size of the audio track
 * @property duration the duration of the audio track
 * @property year the year the track was released
 * @property discNumber the disc number of the track
 * @property trackNumber the track number of the track
 * @property artist the artist of the track
 * @property album the album of the track
 * @property genre the genre of the track
 * @property composer the composer of the track
 */
data class AudioInfoModel(
    val bitrate: String,
    val channels: String,
    val sampleRate: Int,
    val format: String,
    val bitsPerSample: String,
    val size: String,
    val duration: String,
    val year: String,
    val discNumber: String,
    val trackNumber: String,
    val artist: String,
    val album: String,
    val genre: String,
    val composer: String
)
