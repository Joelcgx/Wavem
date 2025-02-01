package com.afterloop.wavem.ui.screens.library

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.afterloop.wavem.R

sealed class MenuOption(
    val id: Int,
    @StringRes val title: Int, // Título de la opción
    @DrawableRes val icon: Int // Ícono de la opción
) {
    data object Play : MenuOption(
        R.string.library_options_play, // ID
        R.string.library_options_play, // Título
        R.drawable.ic_play_filled // Ícono
    )

    data object Convert : MenuOption(
        R.string.library_options_convert, // ID
        R.string.library_options_convert, // Título
        R.drawable.ic_audio // Ícono
    )

    data object Info : MenuOption(
        R.string.library_option_info, // ID
        R.string.library_option_info, // Título
        R.drawable.ic_info // Ícono
    )
}