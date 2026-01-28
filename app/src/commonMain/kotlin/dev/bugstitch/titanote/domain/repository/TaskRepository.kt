package dev.bugstitch.titanote.domain.repository

import dev.bugstitch.titanote.data.database.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun getAllTasks(): Flow<List<Task>>

    suspend fun createTask(task: Task) : Boolean

    suspend fun finishTask(task: Task) : Boolean

    suspend fun deleteTask(task: Task) : Boolean

}