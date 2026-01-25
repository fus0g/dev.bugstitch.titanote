package dev.bugstitch.titanote.di

import dev.bugstitch.titanote.data.room.NotesDao
import dev.bugstitch.titanote.data.room.NotesDatabase
import dev.bugstitch.titanote.presentation.viewmodels.HomePageViewModel
import dev.bugstitch.titanote.presentation.viewmodels.TitanoteViewModel
import dev.bugstitch.titanote.repository.NotesDatabaseRepository
import dev.bugstitch.titanote.repository.NotesDatabaseRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val PlatformModule: Module

val SharedModule = module {

    single<NotesDao> { get<NotesDatabase>().notesDao() }
    single<NotesDatabaseRepository> { NotesDatabaseRepositoryImpl(get()) }

    viewModelOf(::TitanoteViewModel)
    viewModelOf(::HomePageViewModel)
}
