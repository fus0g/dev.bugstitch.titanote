package dev.bugstitch.titanote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

@Composable
fun App(){
    TitanoteTheme {
        val navController = rememberNavController()

        val viewModel = koinViewModel<TitanoteViewModel>()


        NavHost(navController = navController, startDestination = Navigation.HOME,
            modifier = Modifier.background(MaterialTheme.colorScheme.background)){
            composable(route = Navigation.CREATE_NOTE){
                viewModel.setTopBarState(TopBarState.Create)
                EditScreenNew(
                    onSaveClick = {
                            title, content ->
                        viewModel.setNoteTitle(title)
                        viewModel.setNoteContent(content)
                        viewModel.addNote()
                        navController.navigate(Navigation.HOME)
                    }
                )
                //CreateNote(viewModel,navController)
            }
            composable(route = Navigation.HOME) {
                HomePage(viewModel,navController)
            }
            composable(route = Navigation.EDIT_NOTE){
                viewModel.setTopBarState(TopBarState.Create)
                EditScreenNew(
                    onSaveClick = {
                            title, content ->
                        viewModel.setNoteTitle(title)
                        viewModel.setNoteContent(content)
                        viewModel.addNote()
                        navController.navigate(Navigation.HOME)
                    },
                    currentTitle = viewModel.noteTitle.value,
                    currentContent = viewModel.noteContent.value
                )
               // EditScreen(viewModel,navController)
            }
            composable(route = Navigation.PREVIEW_SCREEN)
            {
                val note = viewModel.getCurrentNote()
                if(note!= null)
                {
                    PreviewScreen(note)
                }
            }
            composable(Navigation.START_PAGE) {
                StartPage(viewModel)
            }
        }
    }
}