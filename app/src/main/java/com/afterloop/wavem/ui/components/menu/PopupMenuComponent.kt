package com.afterloop.wavem.ui.components.menu

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.afterloop.wavem.R
import com.afterloop.wavem.ui.screens.library.MenuOption
import com.composables.core.Menu
import com.composables.core.MenuButton
import com.composables.core.MenuContent
import com.composables.core.MenuItem
import com.composables.core.rememberMenuState

@Composable
fun PopupMenuComponent(
    modifier: Modifier = Modifier,
    options: List<MenuOption>,
    onOptionSelected: (MenuOption) -> Unit,
    label: String,
    width: Dp = 140.dp,
    height: Dp = 40.dp
) {
    val state = rememberMenuState(expanded = false)

    Column(modifier = modifier) {
        Menu(state = state, modifier = Modifier.align(Alignment.End)) {
            MenuButton {
                Icon(
                    painter = painterResource(R.drawable.ic_more_vert),
                    contentDescription = label
                )
            }

            MenuContent(
                modifier = Modifier
                    .width(width)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(4.dp),
                enter = scaleIn(
                    animationSpec = tween(durationMillis = 120, easing = LinearOutSlowInEasing),
                    initialScale = 0.8f,
                    transformOrigin = TransformOrigin(0f, 0f)
                ) + fadeIn(tween(durationMillis = 30)),
                exit = scaleOut(
                    animationSpec = tween(durationMillis = 1, delayMillis = 75),
                    targetScale = 1f
                ) + fadeOut(tween(durationMillis = 75))
            ) {
                options.forEachIndexed { index, option ->
                    MenuItem(
                        onClick = {
                            state.expanded = false
                            onOptionSelected(option)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                                .fillMaxSize()
                        ) {
                            Icon(
                                painter = painterResource(id = option.icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = stringResource(option.title),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }

                    // Agregar Divider si no es el último elemento
                    if (index < options.lastIndex) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}