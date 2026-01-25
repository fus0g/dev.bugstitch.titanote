package dev.bugstitch.titanote.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.data.NoteState
import dev.bugstitch.titanote.domain.repository.PlatformUtils
import dev.bugstitch.titanote.repository.NotesDatabaseRepository
import dev.bugstitch.titanote.utils.CustomLog
import dev.bugstitch.titanote.utils.TopBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Clock

class TitanoteViewModel(
    private val notesDatabaseRepository: NotesDatabaseRepository,
    private val platformUtils: PlatformUtils
) : ViewModel() {

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

    private val _noteColor = mutableIntStateOf(0)
    val noteColor: MutableState<Int> = _noteColor

    private val _selectColorState = mutableStateOf(false)
    val selectColorState: MutableState<Boolean> = _selectColorState

    private val _selectNoteLogoState = mutableStateOf(true)
    val selectNoteLogoState: MutableState<Boolean> = _selectNoteLogoState

    private val _noteLogo = mutableIntStateOf(0)
    val noteLogo: MutableState<Int> = _noteLogo


    private val _sideMenuOpen = mutableStateOf(false)
    val sideMenuOpen:MutableState<Boolean> = _sideMenuOpen

    /*val autosave:StateFlow<Boolean?> = preferenceDatastore.autosavePreferences.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )*/
    val autosave:StateFlow<Boolean?> = MutableStateFlow(false)

    private val _encryptionKeyExists:MutableState<Boolean?> = mutableStateOf(null)
    val encryptionKeyExists:MutableState<Boolean?> = _encryptionKeyExists

    private val _encrypted:MutableState<Boolean?> = mutableStateOf(null)
    val encrypted:MutableState<Boolean?> = _encrypted

    private var currentNote: Note? = null


    fun setNoteContent(text:String){
        _noteContent.value = text
    }

    fun setNoteTitle(text:String){
        _noteTitle.value = text
    }

    fun setCurrentNote(note: Note){
        _noteContent.value = note.content
        _noteTitle.value = note.title
        //_noteColor.intValue = note.color
        //_noteLogo.intValue = note.logo
        currentNote = note
    }

    fun nullCurrentNote()
    {
        viewModelScope.launch {
            delay(500)
            currentNote = null
        }
    }

    fun getCurrentNote():Note?
    {
        return currentNote
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
            _noteColor.intValue = 0
            _selectColorState.value = false
            _selectNoteLogoState.value = false
            _noteLogo.intValue = 0
        }
    }

    fun setSelectColorState(state:Boolean){
        _selectColorState.value = state
    }

    fun setNoteColor(color:Int){
        _noteColor.intValue = color
    }

    fun setNoteLogo(logo:Int){
        _noteLogo.intValue = logo
    }

    fun setSelectNoteLogoState(state:Boolean){
        _selectNoteLogoState.value = state
    }

    fun checkEmpty():Boolean{
        return _noteContent.value == "" && _noteTitle.value == ""
    }

    fun openSideMenu(open:Boolean){
        _sideMenuOpen.value = open
    }

    fun addNote()
    {

        val note = Note(
            title = _noteTitle.value,
            content = _noteContent.value,
            date = Clock.System.now(),
          //  color = _noteColor.intValue,
          //  logo = _noteLogo.intValue
        )
        viewModelScope.launch(Dispatchers.IO){
            CustomLog("VM","Adding note")
            notesDatabaseRepository.insertNote(note)
            emptyCurrent()
        }
    }

    fun updateCurrentNote()
    {
        val newNote = currentNote!!.copy(title = _noteTitle.value,
            content = _noteContent.value,
            date = Clock.System.now(),
            //color = _noteColor.intValue,
            //logo = _noteLogo.intValue
        )

        viewModelScope.launch(Dispatchers.IO){
            notesDatabaseRepository.updateNote(newNote)
            currentNote = newNote
        }
    }

    fun deleteNote(note: Note)
    {
        viewModelScope.launch(Dispatchers.IO){
            notesDatabaseRepository.deleteNote(note)
        }
    }

    fun updateAutoSavePreference()
    {
        viewModelScope.launch(Dispatchers.IO) {
           // preferenceDatastore.updateAutosaveKey(!autosave.value!!)
        }
    }

    fun openUrl(url:String){
        platformUtils.openUrl(url)
    }


}