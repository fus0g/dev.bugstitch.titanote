package dev.bugstitch.titanote.ui.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.BadgePlus
import com.composables.icons.lucide.Lucide
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.utils.Navigation

@Composable
fun AddButton(image:ImageVector,onclick: ()->Unit)
{
    IconButton(onClick = onclick, modifier = Modifier
        .size(64.dp)
        .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
        .clip(CircleShape)) {
        Icon(image, contentDescription = stringResource(R.string.add_note),
            tint = MaterialTheme.colorScheme.background)
    }
}