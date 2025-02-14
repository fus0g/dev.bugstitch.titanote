package dev.bugstitch.titanote.ui.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.bugstitch.titanote.R

@Composable
fun SideBarItem(logo:ImageVector,name:String,url:String)
{
    val context = LocalContext.current

    val interactionSource = remember { MutableInteractionSource() }

    Row(modifier = Modifier.fillMaxWidth()
        .statusBarsPadding()
        .padding(start = 8.dp)
        .clickable(onClick = {

            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url))
            context.startActivity(urlIntent)
        }, indication = null,
            interactionSource = interactionSource,
            onClickLabel = "$name ${stringResource(R.string.link)}") ,
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            logo,
            contentDescription = "$name ${stringResource(R.string.logo)}",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(end = 12.dp)
        )
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }

}