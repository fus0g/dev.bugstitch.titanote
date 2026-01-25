package dev.bugstitch.titanote.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import titanote.app.generated.resources.Res
import titanote.app.generated.resources.link
import titanote.app.generated.resources.logo

@Composable
fun SideBarItem(logo:ImageVector,name:String,onClick:()->Unit)
{

    val interactionSource = remember { MutableInteractionSource() }

    Row(modifier = Modifier.fillMaxWidth()
        .statusBarsPadding()
        .padding(start = 8.dp)
        .clickable(onClick = onClick, indication = null,
            interactionSource = interactionSource,
            onClickLabel = "$name ${stringResource(Res.string.link)}") ,
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            logo,
            contentDescription = "$name ${stringResource(Res.string.logo)}",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(end = 12.dp)
        )
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }

}