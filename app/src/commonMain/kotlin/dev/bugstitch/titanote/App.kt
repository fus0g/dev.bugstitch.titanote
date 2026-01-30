package dev.bugstitch.titanote

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.presentation.components.CreateScreenRoute
import dev.bugstitch.titanote.presentation.components.CreateTaskRoute
import dev.bugstitch.titanote.presentation.components.HomeRoute
import dev.bugstitch.titanote.presentation.components.PreviewRoute
import dev.bugstitch.titanote.presentation.pages.CreateTaskScreen
import dev.bugstitch.titanote.presentation.pages.EditScreenNew
import dev.bugstitch.titanote.presentation.pages.HomePage
import dev.bugstitch.titanote.presentation.pages.PreviewScreen
import dev.bugstitch.titanote.presentation.theme.TitanoteTheme
import dev.bugstitch.titanote.presentation.viewmodels.CreateTaskScreenViewModel
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

                val notes by localViewModel.notes.collectAsState()
                val tasks by localViewModel.tasks.collectAsState()

                val searchEnabled by localViewModel.searchEnabled.collectAsState()
                val searchText by localViewModel.searchText.collectAsState()
                val isSideBarOpen by localViewModel.sideBarOpen.collectAsState()

                HomePage(
                    notes = notes,
                    tasks = tasks,

                    searchEnabled = searchEnabled,
                    searchText = searchText,
                    isSideBarOpen = isSideBarOpen,

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

                    onNoteEdit = { note ->
                        navController.navigate(
                            CreateScreenRoute(
                                id = note.id,
                                title = note.title,
                                content = note.content,
                                date = DateConverterObject.instantToTimestamp(note.date)
                            )
                        )
                    },

                    onNoteDelete = localViewModel::deleteNote,
                    onTaskComplete = localViewModel::completeTask,
                    onTaskDelete = localViewModel::deleteTask,

                    onSearchTextChange = localViewModel::setSearchText,
                    onSearchToggle = {
                        localViewModel.setSearchEnabled(!searchEnabled)
                    },

                    onSideBarToggle = {
                        localViewModel.openSideBar(true)
                    },
                    onSideBarClose = {
                        localViewModel.openSideBar(false)
                    },

                    onCreateNote = {
                        navController.navigate(
                            CreateScreenRoute(
                                id = -2,
                                title = "",
                                content = "",
                                date = -20
                            )
                        )
                    },

                    onCreateTask = {
                        navController.navigate(CreateTaskRoute)
                    },

                    onBack = {
                        navController.popBackStack()
                    },

                    onPageChange = localViewModel::setPage
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

            composable<CreateTaskRoute>{

                val localViewModel = koinViewModel<CreateTaskScreenViewModel>()

                CreateTaskScreen(
                    localViewModel.title.value,
                    onTitleChange = {
                        localViewModel.setTitle(it)
                    },
                    localViewModel.description.value,
                    onDescriptionChange = {
                        localViewModel.setDescription(it)
                    },
                    onBackPress = {
                        navController.popBackStack()
                    },
                    onSavePresses ={
                        localViewModel.saveTask()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}