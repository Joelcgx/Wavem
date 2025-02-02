package com.afterloop.wavem.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import com.afterloop.wavem.MainActivity
import java.util.Locale

object LocaleUtils {

    // Aplicar el idioma al contexto
    fun setLocale(context: Context, locale: String): Context {
        val newLocale = Locale(locale)
        Locale.setDefault(newLocale)
        val resources = context.resources
        val config = Configuration(resources.configuration)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(newLocale)
            context.createConfigurationContext(config)
        } else {
            config.locale = newLocale
            resources.updateConfiguration(config, resources.displayMetrics)
            context
        }
    }

    // Actualizar la configuración de recursos
    @SuppressLint("ObsoleteSdkInt")
    fun applyLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val config = Configuration(resources.configuration)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale)
        } else {
            config.locale = locale
        }

        resources.updateConfiguration(config, resources.displayMetrics)
    }

    // Reiniciar la app (versión mejorada)
    fun restartApp(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        (context as? Activity)?.finishAffinity()
    }
}