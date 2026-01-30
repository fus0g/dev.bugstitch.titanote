package dev.bugstitch.titanote.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.data.NoteState
import dev.bugstitch.titanote.data.database.Task
import dev.bugstitch.titanote.domain.repository.TaskRepository
import dev.bugstitch.titanote.repository.NotesDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val notesRepository: NotesDatabaseRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    /* ---------------- UI State (Flows) ---------------- */

    private val _searchEnabled = MutableStateFlow(false)
    val searchEnabled = _searchEnabled.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

    private val _sideBarOpen = MutableStateFlow(false)
    val sideBarOpen = _sideBarOpen.asStateFlow()

    /* ---------------- Raw data ---------------- */

    private val notesFlow = notesRepository.getAllNotes()
    private val tasksFlow = taskRepository.getAllTasks()

    /* ---------------- Filtered UI data ---------------- */

    val notes: StateFlow<List<Note>> =
        combine(
            notesFlow,
            searchText,
            searchEnabled,
            currentPage
        ) { notes, query, enabled, page ->
            if (!enabled || query.isBlank() || page != 0) {
                notes
            } else {
                notes.filter {
                    it.title.contains(query, true) ||
                            it.content.contains(query, true)
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    val tasks: StateFlow<List<Task>> =
        combine(
            tasksFlow,
            searchText,
            searchEnabled,
            currentPage
        ) { tasks, query, enabled, page ->
            if (!enabled || query.isBlank() || page != 1) {
                tasks
            } else {
                tasks.filter {
                    it.title.contains(query, true)
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    /* ---------------- Actions ---------------- */

    fun setSearchEnabled(value: Boolean) {
        _searchEnabled.value = value
        if (!value) _searchText.value = ""
    }

    fun setSearchText(value: String) {
        _searchText.value = value
    }

    fun setPage(page: Int) {
        _currentPage.value = page
        _searchText.value = ""
    }

    fun openSideBar(open: Boolean) {
        _sideBarOpen.value = open
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteNote(note)
        }
    }

    fun completeTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.finishTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(task)
        }
    }
}


data class TaskState(
    val task: List<Task> = emptyList()
)
