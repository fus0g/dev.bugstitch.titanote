package dev.bugstitch.titanote.ui.pages

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composables.icons.lucide.Car
import com.composables.icons.lucide.Cat
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Save
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.TitanoteViewModel
import dev.bugstitch.titanote.ui.components.AddButton
import dev.bugstitch.titanote.ui.components.EditComponent
import dev.bugstitch.titanote.ui.components.LogoButton
import dev.bugstitch.titanote.ui.components.TopBar
import dev.bugstitch.titanote.utils.Logos
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