package dev.bugstitch.titanote.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.Save
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.Trash
import com.composables.icons.lucide.X
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.presentation.theme.ZenColors
import dev.bugstitch.titanote.presentation.viewmodels.TitanoteViewModel
import dev.bugstitch.titanote.utils.LogoString
import dev.bugstitch.titanote.utils.Logos
import dev.bugstitch.titanote.utils.TopBarState
import org.jetbrains.compose.resources.stringResource
import titanote.app.generated.resources.Res
import titanote.app.generated.resources.app_name
import titanote.app.generated.resources.close
import titanote.app.generated.resources.delete
import titanote.app.generated.resources.search
import titanote.app.generated.resources.sidebar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    topBarState: TopBarState,
    searchState: Boolean,
    onSearchButtonPressed:()->Unit,
    onSearchValueChange:(String)->Unit,
    searchValue:String,
    onDelete: () -> Unit = {},
    onSideBarButtonPress:()->Unit,
    onSavePress:()->Unit,
    onUpdatePressed:()->Unit
){

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)){
        TopAppBar(title = { Text(stringResource(Res.string.app_name),
            fontWeight = FontWeight.Bold) },
            actions = {
                when(topBarState)
                {
                    TopBarState.Home ->{
                        IconButton(onClick = onSearchButtonPressed) {
                            if(!searchState)
                            {
                                Icon(Lucide.Search, contentDescription = stringResource(Res.string.search))
                            }else{
                                Icon(Lucide.X, contentDescription = stringResource(Res.string.close))
                            }
                        }
                    }
                    TopBarState.Create -> {
                        IconButton(onClick ={
                            onSavePress()
                        }) {
                            Icon(Lucide.Save, contentDescription = stringResource(Res.string.delete))
                        }
                    }
                    TopBarState.Edit -> {
                        IconButton(onClick ={
                            onUpdatePressed()
                        }) {
                            Icon(Lucide.Save, contentDescription = stringResource(Res.string.delete))
                        }
                    }
                    TopBarState.Preview -> {

                        IconButton(onClick ={
                            onDelete()
                        }) {
                            Icon(Lucide.Trash, contentDescription = stringResource(Res.string.delete))
                        }

                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    onSideBarButtonPress()
                }) { Icon(Lucide.Menu, contentDescription = stringResource(Res.string.sidebar)) }

            })
       AnimatedVisibility(searchState && topBarState == TopBarState.Home, enter = expandVertically()) {
           Row(modifier = Modifier
               .fillMaxWidth()
               .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 8.dp)){
               OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = searchValue,
                   onValueChange = {
                     onSearchValueChange(it)
                   },
                   placeholder = { Text(stringResource(Res.string.search)) })
           }
       }
    }
}