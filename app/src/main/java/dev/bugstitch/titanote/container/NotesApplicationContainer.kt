package dev.bugstitch.titanote.container

import android.content.Context
import dev.bugstitch.titanote.data.room.NotesDatabase
import dev.bugstitch.titanote.repository.NotesDatabaseRepository
import dev.bugstitch.titanote.repository.NotesDatabaseRepositoryImpl

interface NotesApplicationContainer {
    val notesDatabaseRepository:NotesDatabaseRepository
}

class NotesApplicationContainerImpl(private val context: Context) : NotesApplicationContainer {
    override val notesDatabaseRepository: NotesDatabaseRepository by lazy {
        NotesDatabaseRepositoryImpl(NotesDatabase.getDatabase(context).notesDao())
    }
}