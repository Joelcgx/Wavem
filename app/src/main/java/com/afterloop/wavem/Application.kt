package com.afterloop.wavem

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.core.app.ComponentActivity
import com.afterloop.wavem.utils.LocaleUtils
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class WavemApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        applySavedLanguage()
    }

    private fun applySavedLanguage() {
        val prefs = getSharedPreferences("WavemPreferences", Context.MODE_PRIVATE)
        val lang = prefs.getString("app_language", "en") ?: "en"
        LocaleUtils.applyLocale(this, lang)
    }


    override fun attachBaseContext(base: Context) {
        val prefs = base.getSharedPreferences("app_prefs", MODE_PRIVATE)
        val lang = prefs.getString("app_language", "en") ?: "en"
        super.attachBaseContext(updateLocale(base, lang))
    }

    private fun updateLocale(context: Context, lang: String): Context {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration).apply {
            setLocale(locale)
        }
        return context.createConfigurationContext(config)
    }
}