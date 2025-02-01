package com.afterloop.wavem.ui.components.dialogs

import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.afterloop.wavem.R
import com.afterloop.wavem.utils.LocaleUtils
import com.afterloop.wavem.viewmodel.settings.SettingsViewModel

/**
 * A restart dialog that shows a title and a message.
 * This dialog contains a single button with the label "Restart".
 * When the button is clicked, the app will be restarted.
 *
 * @param title The title of the dialog.
 * @param message The message of the dialog.
 * @param context The context of the app.
 */
@Composable
fun AlertRestartDialog(
    title: String, message: String, context: Context
) {
    val settingsVM = hiltViewModel<SettingsViewModel>()

    AlertDialog(onDismissRequest = { settingsVM.setRequestRestart(false) }, title = {
        Text(
            text = title,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary
        )
    }, text = {
        Text(
            text = message,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            color = MaterialTheme.colorScheme.onBackground
        )
    }, confirmButton = {
        TextButton(onClick = { LocaleUtils.restartApp(context) }) {
            Text(
                text = stringResource(R.string.alertDialog_restart_label),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                color = MaterialTheme.colorScheme.primary
            )
        }
    })
}