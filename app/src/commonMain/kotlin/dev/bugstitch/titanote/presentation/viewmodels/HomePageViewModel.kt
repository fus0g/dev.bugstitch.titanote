package dev.bugstitch.titanote.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.data.NoteState
import dev.bugstitch.titanote.repository.NotesDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val notesDatabaseRepository: NotesDatabaseRepository
): ViewModel() {

    val notes:StateFlow<NoteState> = notesDatabaseRepository.getAllNotes().map { NoteState(it) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = NoteState()
        )

    fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            notesDatabaseRepository.deleteNote(note)
        }
    }

}