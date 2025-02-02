package com.afterloop.wavem.ui.components.topbars

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.afterloop.wavem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(onActions: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
            )
        }, actions = {
            IconButton(onClick = { onActions() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_account),
                    contentDescription = "Account",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        })
}