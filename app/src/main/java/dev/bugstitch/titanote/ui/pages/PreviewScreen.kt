package dev.bugstitch.titanote.ui.pages

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pen
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.TitanoteViewModel
import dev.bugstitch.titanote.ui.components.AddButton
import dev.bugstitch.titanote.ui.components.TopBar
import dev.bugstitch.titanote.utils.Navigation
import dev.bugstitch.titanote.utils.TopBarState

@Composable
fun PreviewScreen(viewModel: TitanoteViewModel,navController: NavController) {
    BackHandler {
        navController.navigate(Navigation.HOME)
        viewModel.emptyCurrent()
    }

    viewModel.setTopBarState(TopBarState.Preview)


    Scaffold(topBar = {
        TopBar(viewModel,
            onDelete = {
                navController.navigate(Navigation.HOME)
            })
    },
        floatingActionButton = {
            AddButton(Lucide.Pen) {
                navController.navigate(Navigation.EDIT_NOTE)
            }
        }) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)
            .fillMaxSize()) {

            Text(text = viewModel.noteTitle.value,
                modifier = Modifier.fillMaxWidth())
            Text(text = viewModel.noteContent.value,
                modifier = Modifier.fillMaxWidth())

        }

    }
}