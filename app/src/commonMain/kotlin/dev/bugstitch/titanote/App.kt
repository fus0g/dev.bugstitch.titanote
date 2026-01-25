package dev.bugstitch.titanote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pen
import com.composables.icons.lucide.Plus
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.presentation.components.AddButton
import dev.bugstitch.titanote.presentation.components.HomeRoute
import dev.bugstitch.titanote.presentation.components.PreviewRoute
import dev.bugstitch.titanote.presentation.components.SideBar
import dev.bugstitch.titanote.presentation.components.TopBar
import dev.bugstitch.titanote.presentation.pages.EditScreenNew
import dev.bugstitch.titanote.presentation.pages.HomePage
import dev.bugstitch.titanote.presentation.pages.PreviewScreen
import dev.bugstitch.titanote.presentation.pages.StartPage
import dev.bugstitch.titanote.presentation.theme.TitanoteTheme
import dev.bugstitch.titanote.presentation.viewmodels.HomePageViewModel
import dev.bugstitch.titanote.presentation.viewmodels.TitanoteViewModel
import dev.bugstitch.titanote.utils.DateConverterObject
import dev.bugstitch.titanote.utils.Navigation
import dev.bugstitch.titanote.utils.TopBarState
import org.koin.compose.viewmodel.koinViewModel

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(){
    TitanoteTheme {
        val navController = rememberNavController()

        val globalViewModel = koinViewModel<TitanoteViewModel>()

        Scaffold(
            topBar = {
                TopBar(topBarState = globalViewModel.topBarState.value,
                    searchState = globalViewModel.searchState.value,
                    onDelete = {
                        if(globalViewModel.getCurrentNote() != null)
                        {
                            globalViewModel.deleteNote(globalViewModel.getCurrentNote()!!)
                            globalViewModel.nullCurrentNote()
                        }
                        navController.navigate(HomeRoute)
                    },
                    searchValue = globalViewModel.searchText.value,
                    onSearchValueChange = {
                        globalViewModel.setSearchText(it)
                    },
                    onSearchButtonPressed = {
                        globalViewModel.setSearchState(!globalViewModel.searchState.value)
                    },
                    onSideBarButtonPress = {
                        globalViewModel.openSideMenu(!globalViewModel.sideMenuOpen.value)
                    },
                    onSavePress = {
                        globalViewModel.addNote()
                        globalViewModel.nullCurrentNote()
                        navController.navigate(HomeRoute)
                    },
                    onUpdatePressed = {
                        if(navController.previousBackStackEntry?.destination?.hasRoute<PreviewRoute>() == true)
                        {
                            globalViewModel.updateCurrentNote()
                            navController.navigate(
                                PreviewRoute(
                                    id = globalViewModel.getCurrentNote()!!.id,
                                    title = globalViewModel.noteTitle.value,
                                    content = globalViewModel.noteContent.value,
                                    date = DateConverterObject.instantToTimestamp(globalViewModel.getCurrentNote()!!.date)
                                )
                            )
                        }else{

                            globalViewModel.updateCurrentNote()
                            navController.navigate(HomeRoute)
                            globalViewModel.emptyCurrent()
                        }
                    }
                )
            },
            floatingActionButton = {
                when(globalViewModel.topBarState.value){
                    TopBarState.Home ->{
                        AddButton(Lucide.Plus) {
                            navController.navigate(Navigation.CREATE_NOTE)
                        }
                    }

                    TopBarState.Preview ->{
                        AddButton(Lucide.Pen) {
                            navController.navigate(Navigation.EDIT_NOTE)
                        }
                    }
                    else -> {}
                }
            }
        ) {
            Column(modifier = Modifier.padding(it)) {

                NavHost(navController = navController, startDestination = HomeRoute,
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)){
                    composable(route = Navigation.CREATE_NOTE){
                        globalViewModel.setTopBarState(TopBarState.Create)
                        EditScreenNew(
                            addToViewModel = {
                                     content ->
                                globalViewModel.setNoteContent(content)
                            },
                            currentTitle = globalViewModel.noteTitle.value,
                            currentContent = globalViewModel.noteContent.value,
                            onTitleChange = {t->
                                globalViewModel.setNoteTitle(t)
                            },
                            onBack = {

                                navController.navigate(HomeRoute)
                                globalViewModel.emptyCurrent()
                            }
                        )
                        //CreateNote(viewModel,navController)
                    }
                    composable<HomeRoute> {
                        globalViewModel.setTopBarState(TopBarState.Home)

                        val localViewModel: HomePageViewModel = koinViewModel()

                        val list = localViewModel.notes.collectAsState()

                        HomePage(
                            noteList = list.value.notes,
                            searchState = globalViewModel.searchState.value,
                            searchText = globalViewModel.searchText.value,
                            onNoteClick = {
                                    note ->
                                navController.navigate(
                                    PreviewRoute(
                                        id = note.id,
                                        title = note.title,
                                        content = note.content,
                                        date = DateConverterObject.instantToTimestamp(note.date)
                                    )
                                )
                                globalViewModel.setCurrentNote(note)

                            },
                            onNoteEditClick = {

                            },
                            onNoteDeleteClick = {
                                note ->
                                localViewModel.deleteNote(note)
                            },
                            onBack = {
                                navController.popBackStack()
                            })
                    }
                    composable(route = Navigation.EDIT_NOTE){
                        globalViewModel.setTopBarState(TopBarState.Edit)
                        EditScreenNew(
                            addToViewModel = {
                                content ->
                                globalViewModel.setNoteContent(content)
                            },
                            currentTitle = globalViewModel.noteTitle.value,
                            currentContent = globalViewModel.noteContent.value,
                            onTitleChange = {t->
                                globalViewModel.setNoteTitle(t)
                            },
                            onBack = {
                                if(navController.previousBackStackEntry?.destination?.hasRoute<PreviewRoute>() == true)
                                {
                                    navController.navigate(
                                        PreviewRoute(
                                            id = globalViewModel.getCurrentNote()!!.id,
                                            title = globalViewModel.noteTitle.value,
                                            content = globalViewModel.noteContent.value,
                                            date = globalViewModel.getCurrentNote()!!.date.toEpochMilliseconds()
                                        )
                                    )
                                }else{

                                    navController.navigate(HomeRoute)
                                    globalViewModel.emptyCurrent()
                                }
                            }
                        )
                        // EditScreen(viewModel,navController)
                    }
                    composable<PreviewRoute>
                    { backStackEntry->
                        globalViewModel.setTopBarState(TopBarState.Preview)

                        val previewRoute = backStackEntry.toRoute<PreviewRoute>()

                        val note = Note(
                            id = previewRoute.id,
                            title = previewRoute.title,
                            content = previewRoute.content,
                            date = DateConverterObject.fromTimestamp(previewRoute.date)
                        )

                        globalViewModel.setCurrentNote(note)

                        PreviewScreen(note,
                            onBack = {
                                navController.navigate(HomeRoute)
                                globalViewModel.emptyCurrent()
                            })

                    }
                    composable(Navigation.START_PAGE) {
                        StartPage(globalViewModel)
                    }
                }
            }

        }
        val uriHandler = LocalUriHandler.current
        SideBar(globalViewModel,
            onUrlClick = {
                url->
                uriHandler.openUri(url)
            })

    }
}