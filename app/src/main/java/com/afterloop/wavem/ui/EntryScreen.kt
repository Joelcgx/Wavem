package com.afterloop.wavem.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.afterloop.wavem.data.repository.permissions.PermissionsRepository
import com.afterloop.wavem.navigation.WavemNavigationUI
import com.afterloop.wavem.ui.screens.permission.InitRequestPermissionsUI
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@Composable
fun EntryScreen() {
    RequestPermissions()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RequestPermissions() {
    val permissionsRepository = hiltViewModel<PermissionsRepository>()

    val permissions = permissionsRepository.getPermissions()

    val permissionsStates = rememberMultiplePermissionsState(
        permissions = permissions
    )


    when {
        // All permissions have been granted
        permissionsStates.allPermissionsGranted -> {
            WavemNavigationUI()
        }

        // The user has denied at least one permission
        permissionsStates.shouldShowRationale -> {
            InitRequestPermissionsUI(onRequestPermissionsRepository = {
                permissionsStates.launchMultiplePermissionRequest()
            }, isDeniedPermision = true)
        }

        else -> {
            InitRequestPermissionsUI(
                onRequestPermissionsRepository = { permissionsStates.launchMultiplePermissionRequest() },
                isDeniedPermision = false
            )
        }
    }
}