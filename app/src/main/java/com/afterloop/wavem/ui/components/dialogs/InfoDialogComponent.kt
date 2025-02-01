package com.afterloop.wavem.ui.components.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun InfoDialogComponent(
    title: String,
    onDismissRequest: () -> Unit,
    confirmButtonText: String = "Cerrar",
    content: @Composable () -> Unit,
    showDialog: Boolean
) {
    // Animación de visibilidad con duración reducida
    AnimatedVisibility(
        visible = showDialog,
        enter = fadeIn(animationSpec = tween(durationMillis = 150)) + // Animación de entrada más rápida
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 150)),
        exit = fadeOut(animationSpec = tween(durationMillis = 150)) + // Animación de salida más rápida
                scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 150))
    ) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = { content() },
            confirmButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(
                        text = confirmButtonText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}
