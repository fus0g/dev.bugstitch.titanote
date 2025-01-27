package dev.bugstitch.titanote.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.bugstitch.titanote.data.room.NotesDao
import dev.bugstitch.titanote.data.room.NotesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNotesDao(app:Application):NotesDao{
        return NotesDatabase.getDatabase(app).notesDao()
    }

}