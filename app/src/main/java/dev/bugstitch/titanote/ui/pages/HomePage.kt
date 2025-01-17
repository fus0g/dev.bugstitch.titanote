package dev.bugstitch.titanote.ui.pages

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.composables.icons.lucide.BadgePlus
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.TitanoteViewModel
import dev.bugstitch.titanote.ui.components.AddButton
import dev.bugstitch.titanote.ui.components.NoteCard
import dev.bugstitch.titanote.ui.components.TopBar
import dev.bugstitch.titanote.utils.Navigation
import dev.bugstitch.titanote.utils.TopBarState
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomePage(viewModel: TitanoteViewModel,navController: NavController) {

    BackHandler {}

    viewModel.setTopBarState(TopBarState.Home)

    val noteList = viewModel.notes.collectAsState()

    Scaffold(topBar = {
        TopBar(viewModel)
    },
        floatingActionButton = {
            AddButton(Lucide.Plus) {
                navController.navigate(Navigation.CREATE_NOTE)
            }
    } ) {
        Column(modifier = Modifier.padding(it)) {

            AnimatedVisibility(noteList.value.notes.isEmpty()) {
                Text(stringResource(R.string.no_notes))
            }
            AnimatedVisibility(noteList.value.notes.isNotEmpty()) {
                LazyVerticalGrid(columns = GridCells.FixedSize(175.dp), modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    if(viewModel.searchState.value && viewModel.searchText.value != "")
                    {
                        noteList.value.notes.filter {
                            it.title.lowercase().contains(viewModel.searchText.value.lowercase()) || it.content.lowercase().contains(viewModel.searchText.value.lowercase())
                        }.forEach {
                            note->
                            item {
                                NoteCard(color = note.color,
                                    title = note.title,
                                    content = note.content,
                                    date = note.date,
                                    logo = note.logo,
                                    edit = {
                                        viewModel.setCurrentNote(note)
                                        navController.navigate(Navigation.EDIT_NOTE)
                                    },
                                    delete = {
                                        viewModel.deleteNote(note)
                                    })
                                {
                                    viewModel.setCurrentNote(note)
                                    navController.navigate(Navigation.PREVIEW_SCREEN)
                                }
                            }
                        }
                    }
                    else
                    {
                        noteList.value.notes.forEach { note ->
                            item {
                                NoteCard(color = note.color,
                                    title = note.title,
                                    content = note.content,
                                    date = note.date,
                                    logo = note.logo,
                                    edit = {
                                        viewModel.setCurrentNote(note)
                                        navController.navigate(Navigation.EDIT_NOTE)
                                    },
                                    delete = {
                                        viewModel.deleteNote(note)
                                    })
                                {
                                    viewModel.setCurrentNote(note)
                                    navController.navigate(Navigation.PREVIEW_SCREEN)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}