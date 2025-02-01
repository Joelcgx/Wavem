package com.afterloop.wavem.ui.screens.settings

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.afterloop.wavem.R
import com.afterloop.wavem.ui.components.dialogs.AlertRestartDialog
import com.afterloop.wavem.ui.components.settings.AudioOptions
import com.afterloop.wavem.ui.components.settings.LanguageOptions
import com.afterloop.wavem.ui.components.settings.LanguageRadioOption
import com.afterloop.wavem.ui.components.settings.SettingsDropdownMenuComponent
import com.afterloop.wavem.ui.components.settings.SettingsGroup
import com.afterloop.wavem.ui.components.settings.SettingsSwitchableComponent
import com.afterloop.wavem.viewmodel.settings.SettingsViewModel

@Composable
fun WavemSettingsScreen(settingsVM: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    // State's
    var audioExpanded by remember { mutableStateOf(true) }
    var themeExpanded by remember { mutableStateOf(false) }
    var langExpanded by remember { mutableStateOf(false) }
    val selectedLang by settingsVM.selectedLang.collectAsStateWithLifecycle(
        "en"
    )

    val requiredRestart by settingsVM.requestRestart.collectAsStateWithLifecycle(false)
    // Alert Dialog for restart
    if (requiredRestart) {
        AlertRestartDialog(
            title = stringResource(R.string.alertDialog_restart_title),
            message = stringResource(R.string.alertDialog_restart_message),
            context = context
        )
    }
    BackHandler {
        (context as? Activity)?.finish()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { SettingsTopBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Languages
            SettingsGroup(
                icon = R.drawable.ic_language,
                title = stringResource(R.string.settings_lang_label),
                expanded = langExpanded,
                onExpandedChange = { langExpanded = !langExpanded },
            ) {
                // Language Options
                LanguageOptions.getLanguages().forEach { language ->
                    LanguageRadioOption(
                        title = language.language,
                        description = language.description,
                        selected = selectedLang == language.langCode,
                        onClick = {
                            settingsVM.setSelectedLang(language.langCode) {
                                settingsVM.setRequestRestart(true)
                            }
                        }
                    )
                }
            }
            // Theme
            SettingsGroup(
                title = stringResource(R.string.settings_theme_label),
                expanded = themeExpanded,
                onExpandedChange = { themeExpanded = !themeExpanded },
                icon = R.drawable.ic_theme
            ) {

            }
            // Audio
            SettingsGroup(
                title = stringResource(R.string.settings_audio_label),
                expanded = audioExpanded,
                onExpandedChange = { audioExpanded = !audioExpanded },
                icon = R.drawable.ic_audio
            ) {
                // Audio Options
                AudioOptions.getAudioOptions().forEach { audioOption ->
                    when {
                        // Switch Component
                        audioOption.isSwitch -> {
                            val lowLatencyEnabled by settingsVM.selectedAudioLowLatency.collectAsStateWithLifecycle(
                                false
                            )
                            // Low Latency
                            SettingsSwitchableComponent(
                                title = audioOption.title,
                                description = audioOption.description,
                                checkedState = lowLatencyEnabled,
                                onClick = {
                                    settingsVM.setAudioLowLatency(!lowLatencyEnabled)
                                }
                            )
                        }
                        // DropMenu Component
                        audioOption.isDropMenu -> {
                            // PCM Output mode
                            val selectedPcmOuput by settingsVM.selectedPcmOutput.collectAsState()

                            SettingsDropdownMenuComponent(
                                title = stringResource(R.string.audio_pcm_output_label),
                                description = stringResource(R.string.audio_pcm_output_description),
                                options = listOf<String>("16 Bits", "32 Bits", "Float"),
                                selectedOption = selectedPcmOuput,
                                onOptionsSelected = { selectedOption ->
                                    // Set PCM Output
                                    settingsVM.setPcmOutput(selectedOption)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}