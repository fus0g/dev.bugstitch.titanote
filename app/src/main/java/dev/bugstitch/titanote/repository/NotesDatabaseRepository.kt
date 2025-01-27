package dev.bugstitch.titanote.repository

import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.data.room.NotesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NotesDatabaseRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}

class NotesDatabaseRepositoryImpl @Inject constructor(private val notesDao: NotesDao):NotesDatabaseRepository{

    override fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    override suspend fun insertNote(note: Note) {
        return notesDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return notesDao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) {
        return notesDao.updateNote(note)
    }

}