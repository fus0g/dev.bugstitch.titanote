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
import dev.bugstitch.titanote.presentation.pages.HomePage
import dev.bugstitch.titanote.presentation.pages.PreviewScreen
import dev.bugstitch.titanote.presentation.pages.StartPage
import dev.bugstitch.titanote.presentation.viewmodels.TitanoteViewModel
import dev.bugstitch.titanote.utils.Navigation
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(){
    MaterialTheme {
        val navController = rememberNavController()

        val viewModel = koinViewModel<TitanoteViewModel>()

        val encryptionKeyExists = viewModel.encryptionKeyExists.value

        val startDestination = when (encryptionKeyExists) {
            true -> Navigation.HOME
            false -> Navigation.START_PAGE
            else -> null
        }

        if(startDestination != null)
        {
            NavHost(navController = navController, startDestination = Navigation.HOME,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)){
                composable(route = Navigation.CREATE_NOTE){
                    CreateNote(viewModel,navController)
                }
                composable(route = Navigation.HOME) {
                    HomePage(viewModel,navController)
                }
                composable(route = Navigation.EDIT_NOTE){
                    EditScreen(viewModel,navController)
                }
                composable(route = Navigation.PREVIEW_SCREEN)
                {
                    PreviewScreen(viewModel,navController)
                }
                composable(Navigation.START_PAGE) {
                    StartPage(viewModel)
                }
            }
        }
    }
}