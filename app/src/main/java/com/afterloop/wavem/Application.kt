package com.afterloop.wavem

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@HiltAndroidApp
class WavemApplication : Application() {
    val dataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create(
            migrations = listOf(
                SharedPreferencesMigration(
                    context = this,
                    sharedPreferencesName = "WavemPreferences"
                )
            ),
            produceFile = { this.preferencesDataStoreFile("WavemPreferencesDataStore") }
        )
    }

    // Variable para almacenar el idioma cargado (valor por defecto "en")
    var cachedLanguage: String = "en"

    override fun onCreate() {
        super.onCreate()
        // Precargamos el idioma desde DataStore una única vez al inicio
        runBlocking {
            val prefs = dataStore.data.first()
            cachedLanguage = prefs[stringPreferencesKey("app_language")] ?: "en"
        }
    }
}