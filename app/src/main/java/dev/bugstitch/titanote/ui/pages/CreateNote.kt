package dev.bugstitch.titanote.ui.pages

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Save
import dev.bugstitch.titanote.TitanoteViewModel
import dev.bugstitch.titanote.ui.components.AddButton
import dev.bugstitch.titanote.ui.components.EditComponent
import dev.bugstitch.titanote.ui.components.SideBar
import dev.bugstitch.titanote.ui.components.TopBar
import dev.bugstitch.titanote.utils.Navigation
import dev.bugstitch.titanote.utils.TopBarState

@Composable
fun CreateNote(viewModel: TitanoteViewModel,navController: NavController)
{
    viewModel.setTopBarState(TopBarState.Create)

    val autosave = viewModel.autosave.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {

                }
                Lifecycle.Event.ON_DESTROY -> {
                    if(autosave.value!!)
                    {
                        viewModel.addNote()
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
        if(autosave.value!!)
        {
            viewModel.addNote()
        }
        navController.navigate(Navigation.HOME)
        viewModel.emptyCurrent()
    }
    Scaffold(topBar = {
        TopBar(viewModel)
    },
        floatingActionButton = {
            AddButton(Lucide.Save) {
                navController.navigate(Navigation.HOME)
                viewModel.addNote()
            }
        },
        modifier = Modifier.imePadding()) { innerPadding ->

        EditComponent(modifier = Modifier.padding(innerPadding), viewModel)

    }
    SideBar(viewModel)
}