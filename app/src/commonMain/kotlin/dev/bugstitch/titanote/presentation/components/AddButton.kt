package dev.bugstitch.titanote.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import titanote.app.generated.resources.Res
import titanote.app.generated.resources.add_note

@Composable
fun AddButton(image:ImageVector,onclick: ()->Unit)
{
    IconButton(onClick = onclick, modifier = Modifier
        .size(64.dp)
        .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
        .clip(CircleShape)) {
        Icon(image, contentDescription = stringResource(Res.string.add_note),
            tint = MaterialTheme.colorScheme.background)
    }
}