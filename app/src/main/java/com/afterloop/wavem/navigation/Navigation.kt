package com.afterloop.wavem.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.afterloop.wavem.routes.WavemRoutes
import com.afterloop.wavem.ui.components.profile.MenuItems
import com.afterloop.wavem.ui.components.profile.ProfileMenuDialog
import com.afterloop.wavem.ui.components.profile.profileGoToSettings
import com.afterloop.wavem.ui.components.topbars.TopAppBarComponent
import com.afterloop.wavem.ui.screens.library.LibraryScreen
import com.afterloop.wavem.viewmodel.audio.GetLocalAudio
import kotlinx.coroutines.launch

@Composable
fun WavemNavigationUI() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarComponent {
                scope.launch {
                    showSheet = true
                }
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // Floating Sheet
            ProfileMenuDialog(
                show = showSheet,
                onDismiss = { showSheet = false },
                onMenuItemSelected = { menuItem ->
                    when (menuItem) {
                        is MenuItems.ProfileSettings -> {}
                        is MenuItems.GeneralSettings -> profileGoToSettings(context)
                        is MenuItems.HelpCenter -> {}
                    }
                }
            )
            // Main Content
            WavemNavigation(navController)
        }
    }
}

@Composable
private fun WavemNavigation(navController: NavHostController) {
    // VMs
    val audioVM = hiltViewModel<GetLocalAudio>()

    NavHost(navController = navController, startDestination = WavemRoutes.Library.route) {
        // Library
        composable(route = WavemRoutes.Library.route) {
            LibraryScreen(audioVM)
        }

        // Converter
        composable(route = WavemRoutes.Converter.route) {}

    }
}