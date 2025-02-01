package com.afterloop.wavem.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.afterloop.wavem.routes.WavemRoutes
import com.afterloop.wavem.ui.components.drawers.DraweComponent
import com.afterloop.wavem.ui.components.topbars.TopAppBarComponent
import com.afterloop.wavem.ui.screens.library.LibraryScreen
import com.afterloop.wavem.viewmodel.audio.GetLocalAudio
import kotlinx.coroutines.launch

@Composable
fun WavemNavigationUI() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    DraweComponent(content = {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBarComponent {
                    scope.launch {
                        drawerState.open()
                    }
                }
            }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                WavemNavigation(navController)
            }
        }
    }, drawerState = drawerState, navHostController = navController)
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