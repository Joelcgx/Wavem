package com.afterloop.wavem

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.afterloop.wavem.ui.EntryScreen
import com.afterloop.wavem.ui.theme.WavemTheme
import com.afterloop.wavem.utils.LocaleUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        // Hacemos un cast seguro a WavemApplication
        val appInstance = newBase.applicationContext as? WavemApplication
        val lang = appInstance?.cachedLanguage ?: "en"
        super.attachBaseContext(LocaleUtils.setLocale(newBase, lang))
    }

    // Puedes seguir usando la instancia de la aplicación de forma segura en otros métodos:
    private val app by lazy { application as? WavemApplication }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            // Aquí se usa el dataStore de la instancia precargada, si está disponible
            app?.dataStore?.data
                ?.map { it[stringPreferencesKey("app_language")] ?: "en" }
                ?.distinctUntilChanged() // Solo emite si el valor cambia
                ?.collect { lang ->
                    val currentLang = resources.configuration.locales[0].language
                    if (lang != currentLang) {
                        // Actualiza la variable cache si es necesario
                        app?.cachedLanguage = lang
                        recreate() // Reinicia la actividad para aplicar el nuevo idioma
                    }
                }
        }

        setContent {
            WavemTheme(dynamicColor = false) {
                EntryScreen()
            }
        }
    }
}