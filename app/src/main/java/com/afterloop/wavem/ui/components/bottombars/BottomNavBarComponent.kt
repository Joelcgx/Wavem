package com.afterloop.wavem.ui.components.bottombars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.afterloop.wavem.viewmodel.ui.components.bottombars.BottomBarsVM

@Composable
fun BottomNavBarComponent(navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntry
    val bottomViewModel = hiltViewModel<BottomBarsVM>()
    val selectedIndex by bottomViewModel.selectedIndex.collectAsState()

    BottomAppBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        BottomBarsItems.getBottomBarsItems().forEachIndexed { index, bottomBarsItems ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    bottomViewModel.setSelectedIndex(index)

                    navController.navigate(bottomBarsItems.route) {
                        // Configuración de navegación
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painterResource(
                            if (selectedIndex == index) {
                                bottomBarsItems.selectedIcon
                            } else {
                                bottomBarsItems.unselectedIcon
                            }
                        ), contentDescription = bottomBarsItems.label.toString()
                    )
                },
                label = {
                    Text(
                        text = stringResource(bottomBarsItems.label), style =
                            MaterialTheme.typography.bodySmall
                    )
                }
            )
        }
    }
}