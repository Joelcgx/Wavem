package com.afterloop.wavem.ui.components.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.afterloop.wavem.R

sealed class MenuItems(
    val id: Int,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val isProfileOption: Boolean
) {
    data object ProfileSettings : MenuItems(
        id = R.string.top_bar_menu_profile_settings,
        title = R.string.top_bar_menu_profile_settings,
        icon = R.drawable.ic_account,
        isProfileOption = true
    )

    data object GeneralSettings : MenuItems(
        id = R.string.settings_label,
        title = R.string.settings_label,
        icon = R.drawable.ic_settings_selected,
        isProfileOption = false
    )

    data object HelpCenter : MenuItems(
        id = R.string.top_bar_menu_help,
        title = R.string.top_bar_menu_help,
        icon = R.drawable.ic_help,
        isProfileOption = false
    )

    companion object {
        fun getProfileOptions(): List<MenuItems> = listOf(ProfileSettings)
        fun getGeneralOptions(): List<MenuItems> = listOf(GeneralSettings, HelpCenter)
    }
}