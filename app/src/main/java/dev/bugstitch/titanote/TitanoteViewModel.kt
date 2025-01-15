package dev.bugstitch.titanote

import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.data.NoteState
import dev.bugstitch.titanote.repository.NotesDatabaseRepository
import dev.bugstitch.titanote.utils.TopBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class TitanoteViewModel(private val notesDatabaseRepository: NotesDatabaseRepository) : ViewModel() {

    val notes:StateFlow<NoteState> = notesDatabaseRepository.getAllNotes().map { NoteState(it) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = NoteState()
        )


    private val _noteTitle = mutableStateOf("")
    val noteTitle: MutableState<String> = _noteTitle

    private val _noteContent = mutableStateOf("")
    val noteContent: MutableState<String> = _noteContent

    private val _searchText = mutableStateOf("")
    val searchText: MutableState<String> = _searchText

    private val _searchState = mutableStateOf(false)
    val searchState: MutableState<Boolean> = _searchState

    private val _topBarState = mutableStateOf(TopBarState.Home)
    val topBarState: MutableState<TopBarState> = _topBarState

    private var currentNote:Note? = null

    fun setNoteContent(text:String){
        _noteContent.value = text
    }

    fun setNoteTitle(text:String){
        _noteTitle.value = text
    }

    fun setCurrentNote(note: Note){
        currentNote = note
    }

    fun nullCurrentNote()
    {
        currentNote = null
    }

    fun setSearchState(state:Boolean){
        _searchState.value = state
    }

    fun setTopBarState(state: TopBarState){
        _topBarState.value = state
    }
    fun setSearchText(text:String){
        _searchText.value = text
    }

    fun emptyCurrent()
    {
        viewModelScope.launch {

            delay(500)
            _noteContent.value = ""
            _noteTitle.value = ""
        }
    }

    fun addNote()
    {
        if(_noteContent.value != "" && _noteTitle.value != "")
        {
            val note = Note(
                title = _noteTitle.value,
                content = _noteContent.value,
                date = Date()
            )
            viewModelScope.launch(Dispatchers.IO){
                notesDatabaseRepository.insertNote(note)
                emptyCurrent()
            }
        }
    }

    fun updateCurrentNote()
    {
        if(currentNote != null && _noteContent.value != "" && _noteTitle.value != "")
        {
            val newNote = currentNote!!.copy(title = _noteTitle.value,
                content = _noteContent.value,
                date = Date())

            viewModelScope.launch(Dispatchers.IO){
                notesDatabaseRepository.updateNote(newNote)
            }
        }
    }

    fun deleteNote(note: Note)
    {
        viewModelScope.launch(Dispatchers.IO){
            notesDatabaseRepository.deleteNote(note)
        }
    }


    companion object{
        val factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as TitanoteApplication)
                val repository = app.container.notesDatabaseRepository
                TitanoteViewModel(repository)
            }
        }
    }

}