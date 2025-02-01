package com.afterloop.wavem.ui.screens.permission

import android.os.Process
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.afterloop.wavem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitRequestPermissionsUI(
    onRequestPermissionsRepository: () -> Unit,
    isDeniedPermision: Boolean = false
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.SemiBold
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            // Subtitle
            Text(
                text = stringResource(R.string.permission_setup_title),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily
            )
            // Description
            Text(
                text = stringResource(R.string.permission_setup_message),
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = stringResource(R.string.permissions_required_label),
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily
            )
            Spacer(modifier = Modifier.size(10.dp))
            // Permissions Icons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ShapeDefaults.Small)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                    .padding(8.dp)
            ) {
                PermissionsUI.getPermissionsListUI().forEach {
                    ListPermissionsTable(
                        title = it.title,
                        description = it.description,
                        icon = it.icon
                    )
                }
            }
            // Bottom buttons
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { Process.myPid().also { Process.killProcess(it) } }) {
                    Text(
                        text = stringResource(R.string.permission_setup_cancel_button_text),
                        fontSize = MaterialTheme.typography.labelLarge.fontSize
                    )
                }
                Button(onClick = { onRequestPermissionsRepository() }) {
                    Text(
                        text = stringResource(R.string.permission_setup_button_text),
                        fontSize = MaterialTheme.typography.labelLarge.fontSize
                    )
                }
            }
        }

        // IF isDeniedPermision
        AnimatedVisibility(
            visible = isDeniedPermision,
            enter = fadeIn(animationSpec = tween(300)) + slideInVertically(
                initialOffsetY = { -50 }, // Desplazamiento inicial hacia arriba
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ),
            exit = fadeOut(animationSpec = tween(200)) + slideOutVertically(
                targetOffsetY = { -50 }, // Desplazamiento final hacia arriba
                animationSpec = tween(200, easing = FastOutSlowInEasing)
            )
        ) {
            AlertDialog(
                onDismissRequest = {},
                title = {
                    Text(
                        text = stringResource(R.string.showRationeale_label_dialog),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.showRationale_message_dialog),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                confirmButton = {
                    TextButton(onClick = { onRequestPermissionsRepository() }) {
                        Text(
                            text = stringResource(R.string.permission_setup_button_text),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        Process.myPid().also { Process.killProcess(it) }
                    }) {
                        Text(
                            text = stringResource(R.string.permission_setup_cancel_button_text),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            )
        }
    }
}


@Composable
private fun ListPermissionsTable(
    title: String,
    description: String,
    @DrawableRes icon: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono del permiso
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(40.dp),
            colorFilter = ColorFilter.tint(
                MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Título del permiso
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            // Descripción del permiso
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Justify
            )
        }
    }
}

// Permission UI
data class PermissionsUI(
    val title: String, val description: String, @DrawableRes val icon: Int
) {
    companion object {
        @Composable
        fun getPermissionsListUI(): List<PermissionsUI> {
            return listOf(
                PermissionsUI(
                    title = stringResource(R.string.permissions_audio_title),
                    description = stringResource(R.string.permissions_audio_message),
                    icon = R.drawable.ic_files
                ),
                PermissionsUI(
                    title = stringResource(R.string.permissions_notification_title),
                    description = stringResource(R.string.permissions_notification_message),
                    icon = R.drawable.ic_notify
                )
            )
        }
    }
}