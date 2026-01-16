package dev.bugstitch.titanote.di

import dev.bugstitch.titanote.data.room.NotesDatabase
import dev.bugstitch.titanote.data.room.getNotesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val PlatformModule: Module = module {

    single<NotesDatabase> { getNotesDatabase(androidContext()) }

}