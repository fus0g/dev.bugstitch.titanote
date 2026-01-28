package dev.bugstitch.titanote.data.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.bugstitch.titanote.data.database.Task
import dev.bugstitch.titanote.utils.Constants
import kotlinx.coroutines.flow.Flow

interface TaskDao {

    @Query("SELECT * FROM ${Constants.TASK_TABLE_NAME}")
    fun getAllTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE ${Constants.TASK_TABLE_NAME} SET isCompleted = true WHERE id = :id")
    suspend fun finishTask(id: Int)

}