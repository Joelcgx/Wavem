package com.afterloop.wavem.ui.screens.settings

import android.app.Activity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.afterloop.wavem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar() {
    val context = LocalContext.current
    MediumTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.settings_label),
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        navigationIcon = {
            IconButton(onClick = { (context as? Activity)?.finish() }) {
                Icon(painter = painterResource(R.drawable.ic_back), contentDescription = "Back")
            }
        }
    )
}