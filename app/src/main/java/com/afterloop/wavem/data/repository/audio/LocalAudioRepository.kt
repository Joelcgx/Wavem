package com.afterloop.wavem.data.repository.audio

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.afterloop.wavem.data.model.Audio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalAudioRepository(private val appContext: Context) {

    /**
     * Queries the local audio files stored in the device.
     *
     * This function accesses the MediaStore content provider to retrieve details
     * about audio files present in the external storage of the device. It returns
     * a list of Audio objects, each representing a single audio track with details
     * such as its ID, title, artist, album, album ID, duration, album art, and file path.
     *
     * The query filters out non-music files and files with zero size and sorts
     * the results in ascending order by title. The function is designed to be
     * called within a coroutine context with IO dispatcher for efficient
     * background processing.
     *
     * @return a list of Audio objects representing the local audio files.
     */
    suspend fun queryLocalAudioFiles(): List<Audio> = withContext(Dispatchers.IO) {
        val audioList = mutableListOf<Audio>()
        val contentResolver = appContext.contentResolver

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM_ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
        )

        val selection =
            "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND ${MediaStore.Audio.Media.SIZE} > 0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val albumArtistColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ARTIST)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn) ?: "Unknown"
                val artist = cursor.getString(artistColumn) ?: "Unknown"
                val album = cursor.getString(albumColumn) ?: "Unknown"
                val albumId = cursor.getLong(albumIdColumn)
                val duration = cursor.getLong(durationColumn)
                val path = cursor.getString(dataColumn)

                val albumArtist = cursor.getString(albumArtistColumn)

                val albumArtUri = if (albumArtist.isNullOrEmpty()) {
                    Uri.parse("content://media/external/audio/media/$id/albumart")
                } else {
                    Uri.parse("content://media/external/audio/albumart/$albumId")
                }

                audioList.add(
                    Audio(
                        id = id,
                        title = title,
                        artist = artist,
                        album = album,
                        albumId = albumId,
                        duration = duration,
                        albumArt = albumArtUri,
                        path = path
                    )
                )
                Log.d(
                    "AudioDebug",
                    "ID: $id | Álbum: $album | Álbum Artist: ${cursor.getString(albumArtistColumn) ?: "null"} | Artista: $artist"
                )
            }
        }
        return@withContext audioList
    }
}