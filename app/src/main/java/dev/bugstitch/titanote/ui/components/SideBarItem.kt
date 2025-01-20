package dev.bugstitch.titanote.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SideBarItem(logo:ImageVector,name:String,url:String)
{
    val context = LocalContext.current

    Row(modifier = Modifier.fillMaxWidth()
        .statusBarsPadding()
        .padding(start = 8.dp)
        .clickable {

            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url))
            context.startActivity(urlIntent)
        },
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            logo,
            contentDescription = name,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(end = 12.dp)
        )
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }

}