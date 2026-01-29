package dev.bugstitch.titanote

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.presentation.components.CreateScreenRoute
import dev.bugstitch.titanote.presentation.components.HomeRoute
import dev.bugstitch.titanote.presentation.components.PreviewRoute
import dev.bugstitch.titanote.presentation.pages.EditScreenNew
import dev.bugstitch.titanote.presentation.pages.HomePage
import dev.bugstitch.titanote.presentation.pages.PreviewScreen
import dev.bugstitch.titanote.presentation.theme.TitanoteTheme
import dev.bugstitch.titanote.presentation.viewmodels.EditScreenViewModel
import dev.bugstitch.titanote.presentation.viewmodels.HomePageViewModel
import dev.bugstitch.titanote.presentation.viewmodels.PreviewScreenViewModel
import dev.bugstitch.titanote.utils.DateConverterObject
import org.koin.compose.viewmodel.koinViewModel

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(){
    TitanoteTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = HomeRoute,
            modifier = Modifier.background(MaterialTheme.colorScheme.background)){

            composable<CreateScreenRoute>{ backStackEntry ->

                val createScreenRoute = backStackEntry.toRoute<CreateScreenRoute>()

                var receivedId:Int? = null
                var receivedContent: String? = null

                val localViewModel = koinViewModel<EditScreenViewModel>()

                if(createScreenRoute.id > 0)
                {
                    receivedId = createScreenRoute.id
                    receivedContent = createScreenRoute.content
                    localViewModel.setTitle(createScreenRoute.title)
                }


                EditScreenNew(
                    addToViewModel = {
                            content ->
                        localViewModel.setContent(content)
                    },
                    receivedId = receivedId,
                    receivedContent = receivedContent,
                    title = localViewModel.title.value,
                    onTitleChange = {t->
                        localViewModel.setTitle(t)
                    },
                    onSavePressed = {id->
                        if(id == null)
                        {
                            localViewModel.saveNote()
                            navController.popBackStack()
                        }else{
                            localViewModel.setId(id)
                            localViewModel.updateNote()
                            navController.popBackStack()
                        }
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable<HomeRoute> {
                val localViewModel: HomePageViewModel = koinViewModel()

                val list = localViewModel.notes.collectAsState()

                HomePage(
                    noteList = list.value.notes,
                    searchState = localViewModel.searchState.value,
                    searchText = localViewModel.searchText.value,
                    onNoteClick = { note ->
                        navController.navigate(
                            PreviewRoute(
                                id = note.id,
                                title = note.title,
                                content = note.content,
                                date = DateConverterObject.instantToTimestamp(note.date)
                            )
                        )
                    },
                    onNoteEditClick = { note->

                        navController.navigate(
                            CreateScreenRoute(
                                id = note.id,
                                title = note.title,
                                content = note.content,
                                date = DateConverterObject.instantToTimestamp(note.date)
                            )
                        )
                    },
                    onNoteDeleteClick = { note ->
                        localViewModel.deleteNote(note)
                    },
                    onBack = {
                        navController.popBackStack()
                    },
                    onSearchValueChange = { t->
                        localViewModel.setSearchText(t)
                    },
                    onSearchButtonPressed = {
                        localViewModel.setSearchState(!localViewModel.searchState.value)
                    },
                    isSideBarOpen = localViewModel.sideBarState.value,
                    onSideBarButtonPress = {
                        localViewModel.openSideMenu(true)
                    },
                    onSideBarClose = {
                        localViewModel.openSideMenu(false)
                    },
                    onCreate = {
                        navController.navigate(
                            CreateScreenRoute(
                                id = -2,
                                title = "",
                                content = "",
                                date = -20
                            )
                        )
                    }
                )
            }

            composable<PreviewRoute>
            { backStackEntry->
                val previewRoute = backStackEntry.toRoute<PreviewRoute>()

                val localViewModel = koinViewModel<PreviewScreenViewModel>()

                val note = Note(
                    id = previewRoute.id,
                    title = previewRoute.title,
                    content = previewRoute.content,
                    date = DateConverterObject.fromTimestamp(previewRoute.date)
                )

                PreviewScreen(note,
                    onBack = {
                        navController.navigate(HomeRoute)
                    },
                    onEditClick = {
                        navController.navigate(CreateScreenRoute(
                            id = note.id,
                            title = note.title,
                            content = note.content,
                            date = DateConverterObject.instantToTimestamp(note.date)
                        ))
                    },
                    onDelete = {
                        localViewModel.deleteNote(note.id)
                        navController.popBackStack()
                    }
                )

            }
        }
    }
}