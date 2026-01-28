package dev.bugstitch.titanote.data.room

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.data.database.Task
import dev.bugstitch.titanote.utils.Constants

@DeleteColumn(tableName = Constants.TABLE_NAME, columnName = "color")
@DeleteColumn(tableName = Constants.TABLE_NAME, columnName = "logo")
class Migration_2_3 : AutoMigrationSpec

val Migration_3_4 = object : Migration(3,4) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("""
            CREATE TABLE IF NOT EXISTS ${Constants.TASK_TABLE_NAME} (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                createdAt INTEGER NOT NULL,
                completionTime INTEGER NOT NULL,
                isCompleted INTEGER NOT NULL
            )
        """.trimIndent())
    }
}

@Database(
    entities = [Note::class, Task::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = Migration_2_3::class)
    ]
)
@ConstructedBy(NotesDatabaseConstructor::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    abstract fun taskDao(): TaskDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object NotesDatabaseConstructor :
    RoomDatabaseConstructor<NotesDatabase> {
    override fun initialize(): NotesDatabase
}
