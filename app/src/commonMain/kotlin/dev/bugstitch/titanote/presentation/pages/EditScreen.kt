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
fun EditScreen(viewModel: TitanoteViewModel, navController: NavController) {

    //val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current

    val autosave = viewModel.autosave.collectAsState()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    if(autosave.value!!)
                    {
                        viewModel.updateCurrentNote()
                        navController.navigate(Navigation.HOME)
                    }
                }
                Lifecycle.Event.ON_DESTROY -> {
                    if(autosave.value!!)
                    {
                        viewModel.updateCurrentNote()
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

    viewModel.setTopBarState(TopBarState.Create)
    BackHandler {
        if(viewModel.sideMenuOpen.value)
        {
            viewModel.openSideMenu(false)
        }
        else{

            if(navController.previousBackStackEntry?.destination?.route == Navigation.PREVIEW_SCREEN)
            {   if(autosave.value!!)
            {
                viewModel.updateCurrentNote()
            }
                navController.navigate(Navigation.PREVIEW_SCREEN)
                viewModel.emptyCurrent()
            }else{
                if(autosave.value!!)
                {
                    viewModel.updateCurrentNote()
                }
                navController.navigate(Navigation.HOME)
                viewModel.emptyCurrent()
            }
        }
    }
    Scaffold(topBar = {
        TopBar(viewModel)
    },
        floatingActionButton = {
                AddButton(Lucide.Save) {
                    if(!viewModel.checkEmpty())
                    {
                        viewModel.updateCurrentNote()
                        if(navController.previousBackStackEntry?.destination?.route == Navigation.PREVIEW_SCREEN)
                        {
                            navController.navigate(Navigation.PREVIEW_SCREEN)
                        }else{
                            navController.navigate(Navigation.HOME)
                            viewModel.emptyCurrent()
                            viewModel.nullCurrentNote()
                        }
                    }
                    else{
                    //Toast.makeText(context, context.getString(R.string.should_not_be_empty), Toast.LENGTH_SHORT).show()
                    }
                }
        },
        modifier = Modifier.imePadding()) { innerPadding ->

        EditComponent(modifier = Modifier.padding(innerPadding),
            viewModel)

    }
    SideBar(viewModel)

}