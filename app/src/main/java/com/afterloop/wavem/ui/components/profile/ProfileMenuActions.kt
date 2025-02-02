package com.afterloop.wavem.ui.components.profile

import android.content.Context
import android.content.Intent
import com.afterloop.wavem.activities.WavemSettingsActivity

fun profileGoToSettings(context: Context) {
    val intent = Intent(context, WavemSettingsActivity::class.java)
    context.startActivity(intent)
}