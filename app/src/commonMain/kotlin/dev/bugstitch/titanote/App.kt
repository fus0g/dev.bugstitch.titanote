package dev.bugstitch.titanote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pen
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Save
import dev.bugstitch.titanote.presentation.components.AddButton
import dev.bugstitch.titanote.presentation.components.SideBar
import dev.bugstitch.titanote.presentation.components.TopBar
import dev.bugstitch.titanote.presentation.pages.CreateNote
import dev.bugstitch.titanote.presentation.pages.EditScreen
import dev.bugstitch.titanote.presentation.pages.EditScreenNew
import dev.bugstitch.titanote.presentation.pages.HomePage
import dev.bugstitch.titanote.presentation.pages.PreviewScreen
import dev.bugstitch.titanote.presentation.pages.StartPage
import dev.bugstitch.titanote.presentation.theme.TitanoteTheme
import dev.bugstitch.titanote.presentation.viewmodels.TitanoteViewModel
import dev.bugstitch.titanote.utils.Navigation
import dev.bugstitch.titanote.utils.TopBarState
import org.koin.compose.viewmodel.koinViewModel

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(){
    TitanoteTheme {
        val navController = rememberNavController()
        val backStackEntry = navController.currentBackStackEntryAsState()

        val viewModel = koinViewModel<TitanoteViewModel>()

        Scaffold(
            topBar = {
                TopBar(topBarState = viewModel.topBarState.value,
                    searchState = viewModel.searchState.value,
                    onDelete = {
                        if(viewModel.getCurrentNote() != null)
                        {
                            viewModel.deleteNote(viewModel.getCurrentNote()!!)
                            viewModel.nullCurrentNote()
                        }
                        navController.navigate(Navigation.HOME)
                    },
                    searchValue = viewModel.searchText.value,
                    onSearchValueChange = {
                        viewModel.setSearchText(it)
                    },
                    onSearchButtonPressed = {
                        viewModel.setSearchState(!viewModel.searchState.value)
                    },
                    onSideBarButtonPress = {
                        viewModel.openSideMenu(!viewModel.sideMenuOpen.value)
                    },
                    onSavePress = {
                        viewModel.addNote()
                        viewModel.nullCurrentNote()
                        navController.navigate(Navigation.HOME)
                    },
                    onUpdatePressed = {
                        if(navController.previousBackStackEntry?.destination?.route == Navigation.PREVIEW_SCREEN)
                        {
                            viewModel.updateCurrentNote()
                            navController.navigate(Navigation.PREVIEW_SCREEN)
                            viewModel.emptyCurrent()
                        }else{

                            viewModel.updateCurrentNote()
                            navController.navigate(Navigation.HOME)
                            viewModel.emptyCurrent()
                        }
                    }
                )
            },
            floatingActionButton = {
                when(backStackEntry.value?.destination?.route){
                    Navigation.HOME->{
                        AddButton(Lucide.Plus) {
                            navController.navigate(Navigation.CREATE_NOTE)
                        }
                    }

                    Navigation.PREVIEW_SCREEN ->{
                        AddButton(Lucide.Pen) {
                            navController.navigate(Navigation.EDIT_NOTE)
                        }
                    }
                    else -> {}
                }
            }
        ) {
            Column(modifier = Modifier.padding(it)) {

                NavHost(navController = navController, startDestination = Navigation.HOME,
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)){
                    composable(route = Navigation.CREATE_NOTE){
                        viewModel.setTopBarState(TopBarState.Create)
                        EditScreenNew(
                            addToViewModel = {
                                     content ->
                                viewModel.setNoteContent(content)
                            },
                            currentTitle = viewModel.noteTitle.value,
                            currentContent = viewModel.noteContent.value,
                            onTitleChange = {t->
                                viewModel.setNoteTitle(t)
                            },
                            onBack = {
                                if(navController.previousBackStackEntry?.destination?.route == Navigation.PREVIEW_SCREEN)
                                {
                                    navController.navigate(Navigation.PREVIEW_SCREEN)
                                    viewModel.emptyCurrent()
                                }else{

                                    navController.navigate(Navigation.HOME)
                                    viewModel.emptyCurrent()
                                }
                            }
                        )
                        //CreateNote(viewModel,navController)
                    }
                    composable(route = Navigation.HOME) {
                        HomePage(viewModel,navController,
                            {
                                navController.popBackStack()
                            })
                    }
                    composable(route = Navigation.EDIT_NOTE){
                        viewModel.setTopBarState(TopBarState.Edit)
                        EditScreenNew(
                            addToViewModel = {
                                content ->
                                viewModel.setNoteContent(content)
                            },
                            currentTitle = viewModel.noteTitle.value,
                            currentContent = viewModel.noteContent.value,
                            onTitleChange = {t->
                                viewModel.setNoteTitle(t)
                            },
                            onBack = {
                                if(navController.previousBackStackEntry?.destination?.route == Navigation.PREVIEW_SCREEN)
                                {
                                    navController.navigate(Navigation.PREVIEW_SCREEN)
                                    viewModel.emptyCurrent()
                                }else{

                                    navController.navigate(Navigation.HOME)
                                    viewModel.emptyCurrent()
                                }
                            }
                        )
                        // EditScreen(viewModel,navController)
                    }
                    composable(route = Navigation.PREVIEW_SCREEN)
                    {
                        viewModel.setTopBarState(TopBarState.Preview)
                        val note = viewModel.getCurrentNote()
                        if(note!= null)
                        {
                            PreviewScreen(note,
                                onBack = {
                                    navController.navigate(Navigation.HOME)
                                    viewModel.emptyCurrent()
                                })
                        }
                    }
                    composable(Navigation.START_PAGE) {
                        StartPage(viewModel)
                    }
                }
            }

        }
        SideBar(viewModel)

    }
}