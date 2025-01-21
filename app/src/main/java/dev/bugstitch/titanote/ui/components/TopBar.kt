package dev.bugstitch.titanote.ui.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.Trash
import com.composables.icons.lucide.X
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.TitanoteViewModel
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.ui.theme.ZenColors
import dev.bugstitch.titanote.utils.LogoString
import dev.bugstitch.titanote.utils.Logos
import dev.bugstitch.titanote.utils.TopBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(viewModel: TitanoteViewModel, onDelete: () -> Unit = {}){

    val colorScrollState = rememberScrollState()
    val logoScrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)){
        TopAppBar(title = { Text(stringResource(R.string.app_name),
            fontWeight = FontWeight.Bold) },
            actions = {
                when(viewModel.topBarState.value)
                {
                    TopBarState.Home ->{
                        IconButton(onClick = {
                            viewModel.setSearchState(!viewModel.searchState.value)
                        }) {
                            if(!viewModel.searchState.value)
                            {
                                Icon(Lucide.Search, contentDescription = stringResource(R.string.search))
                            }else{
                                Icon(Lucide.X, contentDescription = stringResource(R.string.close))
                            }
                        }
                    }
                    TopBarState.Create -> {
                        ColorButton(
                            color = ZenColors.NoteColors.colorList[viewModel.noteColor.value],
                        ) {
                                viewModel.setSelectColorState(!viewModel.selectColorState.value)
                        }
                    }
                    TopBarState.Preview -> {

                        IconButton(onClick = {if(viewModel.getCurrentNote() != null){
                            onDelete()
                            viewModel.deleteNote(viewModel.getCurrentNote() as Note)
                            viewModel.emptyCurrent()
                            viewModel.nullCurrentNote()
                        } }) {
                            Icon(Lucide.Trash, contentDescription = stringResource(R.string.delete))
                        }

                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    viewModel.openSideMenu(true)
                }) { Icon(Lucide.Menu, contentDescription = stringResource(R.string.sidebar)) }

            })
       AnimatedVisibility(viewModel.searchState.value && viewModel.topBarState.value == TopBarState.Home, enter = expandVertically()) {
           Row(modifier = Modifier
               .fillMaxWidth()
               .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 8.dp)){
               OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = viewModel.searchText.value,
                   onValueChange = {
                       viewModel.setSearchText(it)
                   },
                   placeholder = { Text(stringResource(R.string.search)) })
           }
       }
        AnimatedVisibility(viewModel.selectColorState.value
                && viewModel.topBarState.value == TopBarState.Create, enter = expandVertically()){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 8.dp)
                    .horizontalScroll(enabled = true, state = colorScrollState)
            ) {
                for(i in 0 until ZenColors.NoteColors.colorList.size){
                    ColorButton(
                        modifier = Modifier.size(48.dp),
                        color = ZenColors.NoteColors.colorList[i]) {
                        viewModel.setNoteColor(i)
                    }
                }
            }
        }
        AnimatedVisibility(viewModel.selectNoteLogoState.value && viewModel.topBarState.value == TopBarState.Create, enter = expandVertically(expandFrom = Alignment.Bottom)){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 8.dp)
                    .horizontalScroll(enabled = true, state = logoScrollState)
            ) {
                for(i in Logos.indices){
                    LogoButton(
                        modifier = Modifier.size(48.dp),
                        icon = Logos[i],
                        contentDescription = LogoString[i],
                        static = false,
                        size = 32.dp) {
                        viewModel.setNoteLogo(i)
                    }
                }
            }
        }
    }
}