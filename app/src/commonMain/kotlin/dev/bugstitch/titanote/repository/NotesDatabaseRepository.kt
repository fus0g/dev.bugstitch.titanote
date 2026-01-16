package dev.bugstitch.titanote.repository

import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.data.room.NotesDao
import dev.bugstitch.titanote.utils.CustomLog
import kotlinx.coroutines.flow.Flow

interface NotesDatabaseRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}

class NotesDatabaseRepositoryImpl(private val notesDao: NotesDao):NotesDatabaseRepository{

    override fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    override suspend fun insertNote(note: Note) {
        CustomLog("REPO",note.toString())
        return notesDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return notesDao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) {
        return notesDao.updateNote(note)
    }

}