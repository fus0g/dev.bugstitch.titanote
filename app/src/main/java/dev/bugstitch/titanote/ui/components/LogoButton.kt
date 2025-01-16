package dev.bugstitch.titanote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun LogoButton(modifier: Modifier = Modifier,icon:ImageVector,static:Boolean,onClick:()->Unit)
{
    Box(modifier = modifier.padding(4.dp))
    {
        Box(modifier = modifier
            .size(48.dp)
            .border(if(!static) 2.dp else 0.dp, if(!static)MaterialTheme.colorScheme.primary else Color.Transparent, CircleShape)
            .clip(CircleShape)
            .clickable { onClick() }, contentAlignment = Alignment.Center)
        {

            Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}