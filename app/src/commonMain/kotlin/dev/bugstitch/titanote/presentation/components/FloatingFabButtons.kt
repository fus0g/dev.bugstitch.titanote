package dev.bugstitch.titanote.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ListChecks
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.SquarePen

@Composable
fun FloatingFabButtons(
    visible: Boolean,
    onCreateNote: () -> Unit = {},
    onCreateTask: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AnimatedFab(
            visible = visible,
            delay = 120,
            reverseDelay = 240
        ) {
            FloatingActionButton(
                onClick = onCreateNote,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Lucide.SquarePen, contentDescription = null)
            }
        }

        AnimatedFab(
            visible = visible,
            delay = 240,
            reverseDelay = 120
        ) {
            FloatingActionButton(
                onClick = onCreateTask,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Lucide.ListChecks, contentDescription = null)
            }
        }
    }
}


@Composable
fun AnimatedFab(
    visible: Boolean,
    delay: Int,
    reverseDelay: Int,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 200,
                delayMillis = delay
            )
        ) + slideInVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy
            ),
            initialOffsetY = { it / 2 }
        ),
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = 150,
                delayMillis = reverseDelay
            )
        ) + slideOutVertically(
            animationSpec = tween(
                durationMillis = 150,
                delayMillis = reverseDelay
            )
        ) { it / 2 }
    ) {
        content()
    }
}
