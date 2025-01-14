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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.TitanoteViewModel
import dev.bugstitch.titanote.utils.Navigation

@Composable
fun CreateNote(viewModel: TitanoteViewModel,navController: NavController)
{
    BackHandler {
        navController.navigate(Navigation.HOME)
        viewModel.emptyCurrent()
    }
    Scaffold(topBar = {

    },
        floatingActionButton = {
            IconButton(onClick = {
                navController.navigate(Navigation.HOME)
                viewModel.addNote()
                                 }, modifier = Modifier.size(50.dp).background(MaterialTheme.colorScheme.primary, shape = CircleShape).clip(
                CircleShape)) {
                Icon(imageVector = Icons.Default.Done, contentDescription = stringResource(R.string.save_note),Modifier.size(50.dp))
            }
        }) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)
            .fillMaxSize()) {

            TextField(value = viewModel.noteTitle.value, onValueChange = {
                viewModel.setNoteTitle(it)
            },
                placeholder = { Text(stringResource(R.string.title)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors().copy(unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent)
            )
            TextField(value = viewModel.noteContent.value, onValueChange = {
                viewModel.setNoteContent(it)
            },
                placeholder = { Text(stringResource(R.string.content)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors().copy(unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent)
            )

        }

    }
}