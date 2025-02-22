package dev.bugstitch.titanote.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Github
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Notebook
import com.composables.icons.lucide.Save
import com.composables.icons.lucide.Shield
import com.composables.icons.lucide.Store
import com.composables.icons.lucide.X
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.TitanoteViewModel

@Composable
fun SideBar(viewModel: TitanoteViewModel){

    val autosave = viewModel.autosave.collectAsState()

    AnimatedVisibility(viewModel.sideMenuOpen.value,
        enter = expandHorizontally(),
        exit = shrinkHorizontally()
    ) {

            Column(Modifier
                .fillMaxSize()
                .shadow(elevation = 2.dp)
                .clickable {  }
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
                        Icon(Lucide.X, contentDescription = stringResource(R.string.close), tint = MaterialTheme.colorScheme.onBackground)
                    }
                }
                Column(modifier = Modifier.fillMaxSize().navigationBarsPadding().padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        SideBarItem(Lucide.Store,stringResource(R.string.store),"https://play.google.com/store/apps/details?id=dev.bugstitch.titanote")
                        SideBarItem(Lucide.Github, stringResource(R.string.github),"https://github.com/fus0g/dev.bugstitch.titanote")
                        SideBarItem(Lucide.Shield,stringResource(R.string.privacyPolicy),"https://github.com/fus0g/dev.bugstitch.titanote/blob/master/PrivacyPolicy.MD")
                        SideBarItem(Lucide.Notebook,stringResource(R.string.tos),"https://github.com/fus0g/dev.bugstitch.titanote/blob/master/ToC.MD")
                    }


                    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        val context = LocalContext.current
                        Column {
                            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                                AnimatedVisibility(autosave.value != null) {
                                    SideBarPreference(Lucide.Save, stringResource(R.string.autosave),autosave.value!!) {
                                        viewModel.updateAutoSavePreference()
                                    }
                                }
                            }
                            Text("${stringResource(R.string.version)} ${context.packageManager.getPackageInfo(context.packageName,0).versionName}",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.labelLarge)
                        }
                    }

                }
            }
    }

}