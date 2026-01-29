package dev.bugstitch.titanote.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.repository.NotesDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock

class PreviewScreenViewModel(
    val notesDatabaseRepository: NotesDatabaseRepository
): ViewModel() {

    fun deleteNote(id: Int){
        val note = Note(
            id = id,
            title = "",
            content = "",
            date = Clock.System.now()
        )

        viewModelScope.launch(Dispatchers.IO) {
            notesDatabaseRepository.deleteNote(note)
        }
    }

}