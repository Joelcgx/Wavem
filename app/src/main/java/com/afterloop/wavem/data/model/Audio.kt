package com.afterloop.wavem.data.model

import android.net.Uri

/**
 * Data class representing a single audio track.
 *
 * @property id the internal database id of the track
 * @property title the title of the track
 * @property artist the artist of the track
 * @property album the album of the track
 * @property albumId the internal database id of the album
 * @property duration the duration of the track in milliseconds
 * @property albumArt the Uri pointing to the album art
 * @property path the path to the audio file
 */
data class Audio(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val albumId: Long,
    val duration: Long,
    val albumArt: Uri,
    val path: String
)