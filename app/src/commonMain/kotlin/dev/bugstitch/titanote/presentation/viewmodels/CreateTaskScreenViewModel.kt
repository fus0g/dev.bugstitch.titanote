package dev.bugstitch.titanote.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bugstitch.titanote.data.database.Task
import dev.bugstitch.titanote.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock

class CreateTaskScreenViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {

    private val _title = mutableStateOf("")
    val title: MutableState<String> = _title

    private val _description = mutableStateOf("")
    val description: MutableState<String> = _description

    fun setTitle(title: String){
        _title.value = title
    }

    fun setDescription(description: String){
        _description.value = description
    }
    fun saveTask(){

        if(title.value.isNotEmpty() && description.value.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                taskRepository.createTask(
                    Task(
                        title = _title.value,
                        description = _description.value,
                        isCompleted = false,
                        createdAt = Clock.System.now(),
                        completionTime = Clock.System.now()
                    )
                )
            }
        }

    }

}