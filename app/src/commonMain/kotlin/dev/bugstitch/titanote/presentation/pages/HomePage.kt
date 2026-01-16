package dev.bugstitch.titanote.presentation.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.composables.icons.lucide.Beer
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import dev.bugstitch.titanote.presentation.components.AddButton
import dev.bugstitch.titanote.presentation.components.NoteCard
import dev.bugstitch.titanote.presentation.components.SideBar
import dev.bugstitch.titanote.presentation.components.TopBar
import dev.bugstitch.titanote.presentation.viewmodels.TitanoteViewModel
import dev.bugstitch.titanote.utils.Navigation
import dev.bugstitch.titanote.utils.TopBarState
import org.jetbrains.compose.resources.stringResource
import titanote.app.generated.resources.Res
import titanote.app.generated.resources.beerJar
import titanote.app.generated.resources.no_notes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomePage(viewModel: TitanoteViewModel, navController: NavController) {

    BackHandler {
        if(viewModel.sideMenuOpen.value)
        {
            viewModel.openSideMenu(false)
        }
    }

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
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    Icon(Lucide.Beer, contentDescription = stringResource(Res.string.beerJar), modifier = Modifier.size(90.dp)
                        .padding(bottom = 20.dp))
                    Text(stringResource(Res.string.no_notes),
                        fontSize = 20.sp,
                    )
                }
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
                                NoteCard(
                                    color = note.color,
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
                                NoteCard(
                                    color = note.color,
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
    SideBar(viewModel)
}