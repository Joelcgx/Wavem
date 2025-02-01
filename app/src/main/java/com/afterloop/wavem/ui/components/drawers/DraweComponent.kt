package com.afterloop.wavem.ui.components.drawers

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.afterloop.wavem.R
import com.afterloop.wavem.activities.WavemSettingsActivity
import com.afterloop.wavem.viewmodel.ui.components.drawers.DrawersViewModel

@Composable
fun DraweComponent(
    content: @Composable () -> Unit,
    drawerState: DrawerState,
    navHostController: NavHostController
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(drawerState, navHostController)
        }
    ) {
        content()
    }
}

@Composable
private fun DrawerContent(
    drawerState: DrawerState,
    navHostController: NavHostController
) {
    val drawerViewModel = hiltViewModel<DrawersViewModel>()
    val selectedDrawerIndex by drawerViewModel.selectedIndex.collectAsState(0)
    val context = LocalContext.current

    ModalDrawerSheet(
        drawerState = drawerState,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)),
        drawerContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        drawerTonalElevation = 8.dp
    ) {
        Text(
            "Drawer title", modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalDivider()
        DrawerItems.getDrawerItems().forEachIndexed { index, drawerItems ->
            NavigationDrawerItem(
                label = { Text(text = drawerItems.title) },
                selected = index == selectedDrawerIndex,
                onClick = {
                    drawerViewModel.setSelectedIndex(index)

                    navHostController.navigate(route = drawerItems.route) {
                        navHostController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    val icon =
                        if (index == selectedDrawerIndex) drawerItems.selectedIcon else drawerItems.unselectedIcon
                    Icon(painter = painterResource(icon), contentDescription = drawerItems.title)
                }
            )
        }
        // Bottom section
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Version 1.0.0", modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(
                onClick = {
                    val settingsIntent = Intent(context, WavemSettingsActivity::class.java)
                    context.startActivity(settingsIntent)
                }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_settings_selected),
                    contentDescription = stringResource(R.string.app_version),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}