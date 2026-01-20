package dev.bugstitch.titanote.data.room

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.migration.AutoMigrationSpec
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.utils.Constants

@DeleteColumn(tableName = Constants.TABLE_NAME, columnName = "color")
@DeleteColumn(tableName = Constants.TABLE_NAME, columnName = "logo")
class Migration_2_3 : AutoMigrationSpec

@Database(
    entities = [Note::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = Migration_2_3::class)
    ]
)
@ConstructedBy(NotesDatabaseConstructor::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object NotesDatabaseConstructor :
    RoomDatabaseConstructor<NotesDatabase> {
    override fun initialize(): NotesDatabase
}
