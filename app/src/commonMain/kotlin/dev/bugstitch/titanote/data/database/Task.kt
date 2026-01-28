package dev.bugstitch.titanote.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.bugstitch.titanote.utils.Constants
import dev.bugstitch.titanote.utils.DateConverter
import kotlin.time.Instant

@Entity(tableName = Constants.TASK_TABLE_NAME)
@TypeConverters(DateConverter::class)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title:String,
    val description:String,
    val createdAt: Instant,
    val completionTime: Instant,
    val isCompleted: Boolean
)