package dev.bugstitch.titanote.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import titanote.app.generated.resources.Res
import titanote.app.generated.resources.logo

@Composable
fun SideBarPreference(logo: ImageVector, name:String,state:Boolean,onStateChange:(Boolean) -> Unit)
{
    Row(modifier = Modifier.fillMaxWidth()
        .statusBarsPadding()
        .padding(start = 8.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row{
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
        Switch(
            checked = state,
            onCheckedChange = onStateChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.surface,
                checkedBorderColor = MaterialTheme.colorScheme.onSurface,
                checkedIconColor = MaterialTheme.colorScheme.onBackground,
                checkedThumbColor = MaterialTheme.colorScheme.onBackground
            )
        )

    }

}