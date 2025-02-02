package com.afterloop.wavem.ui.components.bottombars

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.afterloop.wavem.R
import com.afterloop.wavem.routes.WavemRoutes

data class BottomBarsItems(
    @StringRes val label: Int,
    var route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
) {
    companion object {
        @Composable
        fun getBottomBarsItems(): List<BottomBarsItems> {
            return listOf(
                BottomBarsItems(
                    label = R.string.drawer_label_library,
                    route = WavemRoutes.Library.route,
                    selectedIcon = R.drawable.ic_library_selected,
                    unselectedIcon = R.drawable.ic_library_unselected
                ),
                BottomBarsItems(
                    label = R.string.drawer_label_converter,
                    route = WavemRoutes.Converter.route,
                    selectedIcon = R.drawable.ic_convert_selected,
                    unselectedIcon = R.drawable.ic_convert_unselected
                )
            )
        }
    }
}
