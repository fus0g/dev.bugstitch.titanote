package dev.bugstitch.titanote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorButton(modifier:Modifier = Modifier,color:Color,onClick:()->Unit){
    Box(modifier = Modifier.padding(start = 4.dp, end = 4.dp))
    {
        Box(modifier = modifier
            .size(40.dp)
            .border(color = MaterialTheme.colorScheme.primary, width = 2.dp, shape = CircleShape)
            .padding(4.dp)
            .background(color, shape = CircleShape)
            .clip(CircleShape)
            .clickable { onClick() })
    }

}