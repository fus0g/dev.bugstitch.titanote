package dev.bugstitch.titanote.data.repository

import dev.bugstitch.titanote.data.database.Task
import dev.bugstitch.titanote.data.room.TaskDao
import dev.bugstitch.titanote.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao
): TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    override suspend fun createTask(task: Task): Boolean {
        try {
            taskDao.insertTask(task)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun finishTask(task: Task): Boolean {
        try {
            taskDao.finishTask(task.id)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun deleteTask(task: Task): Boolean {
        try {
            taskDao.deleteTask(task)
            return true
        }catch (e: Exception){
            return false
        }
    }
}