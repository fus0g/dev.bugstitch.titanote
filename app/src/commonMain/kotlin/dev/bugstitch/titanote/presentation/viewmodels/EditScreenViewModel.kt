package dev.bugstitch.titanote.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.repository.NotesDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock

class EditScreenViewModel(
    private val notesRepository: NotesDatabaseRepository
): ViewModel() {

    private val _title = mutableStateOf("")
    val title: MutableState<String> = _title

    private val _content = mutableStateOf("")
    val content: MutableState<String> = _content

    private val _id = mutableStateOf<Int?>(null)
    val id: MutableState<Int?> = _id


    fun setTitle(title: String){
        _title.value = title
    }

    fun setContent(content: String){
        _content.value = content
    }

    fun setId(id:Int?){
        _id.value = id
    }

    fun saveNote(){
        val note = Note(
            title = _title.value,
            content = _content.value,
            date = Clock.System.now(),
        )
        viewModelScope.launch(Dispatchers.IO){
            notesRepository.insertNote(note)
        }
    }

    fun updateNote(){
        if(_id.value != null)
        {
            val note = Note(
                id = _id.value!!,
                title = _title.value,
                content = _content.value,
                date = Clock.System.now(),
            )
            viewModelScope.launch(Dispatchers.IO) {
                notesRepository.updateNote(note)
            }
        }
    }

}