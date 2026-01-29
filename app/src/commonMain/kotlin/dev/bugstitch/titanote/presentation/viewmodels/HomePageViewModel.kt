package dev.bugstitch.titanote.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

    private val _searchState = mutableStateOf(false)
    val searchState: MutableState<Boolean> = _searchState

    private val _searchText = mutableStateOf("")
    val searchText: MutableState<String> = _searchText

    private val _sideBarState = mutableStateOf(false)
    val sideBarState: MutableState<Boolean> = _sideBarState

    fun setSearchState(state:Boolean){
        _searchState.value = state
    }

    fun setSearchText(text:String){
        _searchText.value = text
    }

    fun openSideMenu(state: Boolean){
        _sideBarState.value = state
    }

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