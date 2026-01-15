package dev.bugstitch.titanote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.bugstitch.titanote.repository.NotesDatabaseRepository
import dev.bugstitch.titanote.repository.NotesDatabaseRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppRepository {

    @Binds
    @Singleton
    abstract fun bindNotesDatabaseRepository(notesDatabaseRepositoryImpl: NotesDatabaseRepositoryImpl):NotesDatabaseRepository

}