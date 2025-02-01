package com.afterloop.wavem

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.afterloop.wavem.ui.EntryScreen
import com.afterloop.wavem.ui.theme.WavemTheme
import com.afterloop.wavem.utils.LocaleUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleUtils.setLocale(
                newBase,
                LocaleUtils.getSavedLanguage(newBase)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WavemTheme(
                dynamicColor = false
            ) {
                EntryScreen()
            }
        }
    }
}