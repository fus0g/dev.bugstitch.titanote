package dev.bugstitch.titanote.presentation.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Beer
import com.composables.icons.lucide.Lucide
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.presentation.components.NoteCard
import org.jetbrains.compose.resources.stringResource
import titanote.app.generated.resources.Res
import titanote.app.generated.resources.beerJar
import titanote.app.generated.resources.no_notes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomePage(
    noteList:List<Note>,
    searchState: Boolean,
    searchText: String,
    onBack:()->Unit,
    onNoteClick:(Note)->Unit,
    onNoteEditClick:(Note)->Unit,
    onNoteDeleteClick:(Note)->Unit
) {

    BackHandler {
        onBack()
    }


    Column {

        AnimatedVisibility(noteList.isEmpty()) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                Icon(Lucide.Beer, contentDescription = stringResource(Res.string.beerJar), modifier = Modifier.size(90.dp)
                    .padding(bottom = 20.dp))
                Text(stringResource(Res.string.no_notes),
                    fontSize = 20.sp,
                )
            }
        }
        AnimatedVisibility(noteList.isNotEmpty()) {
            LazyColumn( modifier = Modifier.padding(4.dp)) {
                if(searchState && searchText != "")
                {
                    noteList.filter {
                        it.title.lowercase().contains(searchText.lowercase()) || it.content.lowercase().contains(searchText.lowercase())
                    }.forEach {
                            note->
                        item {
                            NoteCard(
                                color = 0,
                                title = note.title,
                                content = note.content,
                                date = note.date,
                                logo = 0,
                                edit = {
                                    onNoteEditClick(note)
                                },
                                delete = {
                                    onNoteDeleteClick(note)
                                })
                            {
                                onNoteClick(note)
                            }
                        }
                    }
                }
                else
                {
                    noteList.forEach { note ->
                        item {
                            NoteCard(
                                color = 0,
                                title = note.title,
                                content = note.content,
                                date = note.date,
                                logo = 0,
                                edit = {
                                    onNoteEditClick(note)
                                },
                                delete = {
                                    onNoteDeleteClick(note)
                                })
                            {
                                onNoteClick(note)
                            }
                        }
                    }
                }
            }
        }
    }
}