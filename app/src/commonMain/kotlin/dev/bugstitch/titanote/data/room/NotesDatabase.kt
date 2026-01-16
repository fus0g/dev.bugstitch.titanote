package dev.bugstitch.titanote.data.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.bugstitch.titanote.data.Note

@Database(
    entities = [Note::class],
    version = 2,
    exportSchema = true
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
