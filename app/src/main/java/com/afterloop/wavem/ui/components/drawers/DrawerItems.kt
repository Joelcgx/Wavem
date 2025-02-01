package com.afterloop.wavem.ui.components.drawers

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.afterloop.wavem.R
import com.afterloop.wavem.routes.WavemRoutes

/**
 * Data class that represents the items in the navigation drawer
 *
 * @property title the title of the item
 * @property route the route associated with the item
 * @property selectedIcon the icon to show when the item is selected
 * @property unselectedIcon the icon to show when the item is not selected
 */
data class DrawerItems(
    val title: String,
    val route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
) {
    companion object {
        @Composable
        fun getDrawerItems(): List<DrawerItems> {
            return listOf(
                DrawerItems(
                    title = stringResource(R.string.drawer_label_library),
                    route = WavemRoutes.Library.route,
                    selectedIcon = R.drawable.ic_library_selected,
                    unselectedIcon = R.drawable.ic_library_unselected
                ),
                DrawerItems(
                    title = stringResource(R.string.drawer_label_converter),
                    route = WavemRoutes.Converter.route,
                    selectedIcon = R.drawable.ic_convert_selected,
                    unselectedIcon = R.drawable.ic_convert_unselected
                )
            )
        }
    }
}