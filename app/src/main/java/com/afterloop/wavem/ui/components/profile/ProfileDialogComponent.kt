package com.afterloop.wavem.ui.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.afterloop.wavem.R

@Composable
fun ProfileMenuDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onMenuItemSelected: (MenuItems) -> Unit
) {
    if (show) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(8.dp),
                shape = MaterialTheme.shapes.extraLarge,
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Header
                    Row(
                        modifier = Modifier.padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(0.5f)
                    )

                    // Profile Options Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Card(
                            modifier = Modifier,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            elevation = CardDefaults.cardElevation(8.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column {
                                MenuItems.getProfileOptions().forEach { item ->
                                    ProfileMenuItemComponent(
                                        item = item,
                                        onClick = {
                                            onMenuItemSelected(item)
                                            onDismiss()
                                        },
                                        height = 50.dp
                                    )
                                    if (item != MenuItems.getProfileOptions().last()) {
                                        HorizontalDivider(
                                            modifier = Modifier,
                                            thickness = 0.5.dp,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                                0.5f
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // General Options
                    Column(modifier = Modifier.padding(8.dp)) {
                        MenuItems.getGeneralOptions().forEach { item ->
                            ProfileMenuItemComponent(
                                item = item,
                                onClick = {
                                    onMenuItemSelected(item)
                                    onDismiss()
                                }
                            )
                            if (item != MenuItems.getGeneralOptions().last()) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    thickness = 0.5.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}