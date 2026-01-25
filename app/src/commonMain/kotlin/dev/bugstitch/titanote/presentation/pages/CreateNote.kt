package dev.bugstitch.titanote.presentation.pages

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Save
import dev.bugstitch.titanote.presentation.components.AddButton
import dev.bugstitch.titanote.presentation.components.EditComponent
import dev.bugstitch.titanote.presentation.components.SideBar
import dev.bugstitch.titanote.presentation.components.TopBar
import dev.bugstitch.titanote.presentation.viewmodels.TitanoteViewModel
import dev.bugstitch.titanote.utils.Navigation
import dev.bugstitch.titanote.utils.TopBarState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateNote(viewModel: TitanoteViewModel, navController: NavController)
{
    viewModel.setTopBarState(TopBarState.Create)

    val autosave = viewModel.autosave.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    if(autosave.value!!)
                    {
                        if(!viewModel.checkEmpty())
                        {
                            viewModel.addNote()
                            navController.navigate(Navigation.HOME)
                        }
                    }
                }
                Lifecycle.Event.ON_DESTROY -> {
                    if(autosave.value!!)
                    {
                        if(!viewModel.checkEmpty())
                        {
                            viewModel.addNote()
                        }
                    }
                }
                else -> {}
            }
        }


        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    BackHandler {
        if(viewModel.sideMenuOpen.value)
        {
            viewModel.openSideMenu(false)
        }
        else{
            if(autosave.value!!)
            {
                if(!viewModel.checkEmpty())
                {
                    viewModel.addNote()
                }
            }
            navController.navigate(Navigation.HOME)
            viewModel.emptyCurrent()
        }

    }
    EditComponent(modifier = Modifier.padding(), viewModel)
}