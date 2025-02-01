package com.afterloop.wavem.data.repository.permissions

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionsRepository @Inject constructor() : ViewModel() {
    private val listOfPermissions =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            listOf(
                "android.permission.POST_NOTIFICATIONS", "android.permission.READ_MEDIA_AUDIO"
            )
        } else {
            listOf(
                "android.permission.POST_NOTIFICATIONS", "android.permission.READ_EXTERNAL_STORAGE"
            )
        }

    fun getPermissions(): List<String> = listOfPermissions
}