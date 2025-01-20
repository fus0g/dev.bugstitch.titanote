package dev.bugstitch.titanote.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.bugstitch.titanote.ui.theme.ZenColors

@Composable
fun LogoButton(modifier: Modifier = Modifier,size:Dp,icon:ImageVector,static:Boolean,onClick:()->Unit = {})
{
    Box()
    {
        Box(modifier = modifier
            .size(size)
            .clip(CircleShape)
            .clickable { onClick() }, contentAlignment = Alignment.Center)
        {

            Icon(imageVector = icon, contentDescription = null, tint = if(static)ZenColors.Night else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(size-10.dp))
        }
    }
}