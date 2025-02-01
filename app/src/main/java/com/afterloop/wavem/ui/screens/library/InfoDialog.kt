package com.afterloop.wavem.ui.screens.library

import android.util.Log
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.afterloop.wavem.R
import com.afterloop.wavem.data.model.Audio
import com.afterloop.wavem.ui.components.dialogs.InfoDialogComponent
import com.afterloop.wavem.viewmodel.audio.AudioInfoVM
import java.util.Locale

@Composable
fun InfoDialog(
    onShowDialog: () -> Unit,
    audio: Audio,
    showDialog: Boolean,
    audioInfoVM: AudioInfoVM = hiltViewModel()
) {
    LaunchedEffect(showDialog) {
        if (showDialog) {
            audioInfoVM.loadAudioDetails(audio.path)
        }
    }
    val audioInfo by audioInfoVM.audioInfoFlow.collectAsState(null)

    if (audioInfo != null) {
        Log.d("AudioInfo", audioInfo.toString())
    }

    // Mostrar el diálogo animado
    InfoDialogComponent(
        title = stringResource(R.string.dialog_info_title),
        onDismissRequest = { onShowDialog() },
        confirmButtonText = stringResource(R.string.dialog_info_close),
        content = {
            Column {
                Text(
                    text = stringResource(R.string.dialog_track_path),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = audio.path,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    //Left
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        //Size
                        Text(
                            text = stringResource(R.string.dilaog_track_size),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.size.toString() + " MB",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        // Bitrate
                        Text(
                            text = stringResource(R.string.dialog_track_bitrate),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.bitrate.toString() + " Kbps",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        // Channels
                        Text(
                            text = stringResource(R.string.dialog_track_channels),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.channels.toString()
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        // Duration
                        Text(
                            text = stringResource(R.string.dialog_track_duration),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.duration.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        // Disc number
                        Text(
                            text = stringResource(R.string.dialog_track_disc_number),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.discNumber.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        // Artist
                        Text(
                            text = stringResource(R.string.dialog_track_artist),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.artist.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                        )
                        // Composer
                        Text(
                            text = stringResource(R.string.dialog_track_composer),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.composer.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            softWrap = true,
                            modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                        )
                    }
                    //Right
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        //Format
                        Text(
                            text = stringResource(R.string.dialog_track_format),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.format.toString().uppercase(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        // Samplerate
                        Text(
                            text = stringResource(R.string.dialog_track_samplerate),
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            audioInfo?.sampleRate.toString() + " Hz",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        // Bits per sample
                        Text(
                            text = stringResource(R.string.dialog_track_bits_per_sample),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.bitsPerSample.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        // Date
                        Text(
                            text = stringResource(R.string.dialog_track_date),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.year.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        // Number of tracks
                        Text(
                            text = stringResource(R.string.dialog_track_number_of_tracks),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.trackNumber.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        // Album
                        Text(
                            text = stringResource(R.string.dialog_track_album),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.album.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        // Genre
                        Text(
                            text = stringResource(R.string.dialog_track_genre),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            audioInfo?.genre.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        showDialog = showDialog
    )
}