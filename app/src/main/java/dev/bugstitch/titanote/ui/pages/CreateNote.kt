package dev.bugstitch.titanote.ui.pages

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Save
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.TitanoteViewModel
import dev.bugstitch.titanote.ui.components.AddButton
import dev.bugstitch.titanote.ui.components.EditComponent
import dev.bugstitch.titanote.ui.components.TopBar
import dev.bugstitch.titanote.utils.Navigation
import dev.bugstitch.titanote.utils.TopBarState

@Composable
fun CreateNote(viewModel: TitanoteViewModel,navController: NavController)
{
    val context = LocalContext.current

    viewModel.setTopBarState(TopBarState.Create)

    BackHandler {
        navController.navigate(Navigation.HOME)
        viewModel.emptyCurrent()
    }
    Scaffold(topBar = {
        TopBar(viewModel)
    },
        floatingActionButton = {
            AddButton(Lucide.Save) {
                if(!viewModel.checkEmpty())
                {
                    navController.navigate(Navigation.HOME)
                    viewModel.addNote()
                }
                else{
                    Toast.makeText(context, context.getString(R.string.should_not_be_empty), Toast.LENGTH_SHORT).show()
                }
            }
        },
        modifier = Modifier.imePadding()) { innerPadding ->

        EditComponent(modifier = Modifier.padding(innerPadding), viewModel)

    }
}