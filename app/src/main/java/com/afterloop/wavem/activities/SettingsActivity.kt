package com.afterloop.wavem.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.afterloop.wavem.WavemApplication
import com.afterloop.wavem.ui.screens.settings.WavemSettingsScreen
import com.afterloop.wavem.ui.theme.WavemTheme
import com.afterloop.wavem.utils.LocaleUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WavemSettingsActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        val appInstance = newBase.applicationContext as? WavemApplication
        val lang = appInstance?.cachedLanguage ?: "en"
        super.attachBaseContext(LocaleUtils.setLocale(newBase, lang))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WavemTheme(dynamicColor = false) {
                WavemSettingsScreen()
            }
        }
    }
}