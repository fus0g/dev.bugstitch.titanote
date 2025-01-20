package dev.bugstitch.titanote.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composables.icons.lucide.Github
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Notebook
import com.composables.icons.lucide.Shield
import com.composables.icons.lucide.X
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.TitanoteViewModel

@Composable
fun SideBar(viewModel: TitanoteViewModel){

    AnimatedVisibility(viewModel.sideMenuOpen.value,
        enter = expandHorizontally(),
        exit = shrinkHorizontally()
    ) {

            Column(Modifier
                .fillMaxSize()
                .shadow(elevation = 2.dp)
                .background(MaterialTheme.colorScheme.surface)){

                Row(modifier = Modifier.fillMaxWidth()
                    .statusBarsPadding()
                    .padding(start = 8.dp)
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.app_name),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = {viewModel.openSideMenu(false)}) {
                        Icon(Lucide.X, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
                    }
                }
                Column(modifier = Modifier.fillMaxSize().navigationBarsPadding().padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        SideBarItem(Lucide.Github,"Github","https://github.com/fus0g/dev.bugstitch.titanote")
                        SideBarItem(Lucide.Shield,"Privacy Policy","https://github.com/fus0g/dev.bugstitch.titanote/blob/master/PrivacyPolicy.MD")
                        SideBarItem(Lucide.Notebook,"Terms of Service","https://github.com/fus0g/dev.bugstitch.titanote/blob/master/ToC.MD")
                    }


                    Row(modifier = Modifier.fillMaxWidth()) {
                        val context = LocalContext.current
                        Text("${stringResource(R.string.version)} ${context.packageManager.getPackageInfo(context.packageName,0).versionName}",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelLarge)
                    }

                }
            }
    }

}