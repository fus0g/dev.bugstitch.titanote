package dev.bugstitch.titanote.ui.pages

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.TitanoteViewModel
import dev.bugstitch.titanote.utils.Navigation
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Scanner

@Composable
fun HomePage(viewModel: TitanoteViewModel,navController: NavController) {

    BackHandler {}

    val noteList = viewModel.notes.collectAsState()

    Scaffold(floatingActionButton = {
        IconButton(onClick = {
            navController.navigate(Navigation.CREATE_NOTE)
        }, modifier = Modifier
            .size(65.dp)
            .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            .clip(CircleShape)) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_note), tint = MaterialTheme.colorScheme.onPrimary)
        }
    } ) {
        Column(modifier = Modifier.padding(it)) {

            AnimatedVisibility(noteList.value.notes.isEmpty()) {
                Text(stringResource(R.string.no_notes))
            }
            AnimatedVisibility(noteList.value.notes.isNotEmpty()) {
                LazyColumn {
                    noteList.value.notes.forEach { note ->
                        item {
                            Column(modifier = Modifier.clickable {
                                viewModel.setCurrentNote(note)
                                viewModel.setNoteTitle(note.title)
                                viewModel.setNoteContent(note.content)
                                navController.navigate(Navigation.PREVIEW_SCREEN)
                            }) {
                                Text(note.title)
                                Text(note.content)
                                Text(text = SimpleDateFormat("dd/mm/yyyy 'at' hh:mm a",Locale.getDefault()).format(note.date).toString())

                                Row{
                                    Button(onClick = {
                                        viewModel.deleteNote(note)
                                    }) { Text(stringResource(R.string.delete)) }
                                    Button(onClick = {
                                        viewModel.setNoteTitle(note.title)
                                        viewModel.setNoteContent(note.content)
                                        viewModel.setCurrentNote(note)
                                        navController.navigate(Navigation.EDIT_NOTE)
                                    }) { Text(stringResource(R.string.edit)) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}