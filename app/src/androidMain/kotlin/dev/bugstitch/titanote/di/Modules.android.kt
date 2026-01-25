package dev.bugstitch.titanote.di

import dev.bugstitch.titanote.data.repository.PlatformUtilsImpl
import dev.bugstitch.titanote.data.room.NotesDatabase
import dev.bugstitch.titanote.data.room.getNotesDatabase
import dev.bugstitch.titanote.domain.repository.PlatformUtils
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val PlatformModule: Module = module {

    single<NotesDatabase> { getNotesDatabase(androidContext()) }
    single<PlatformUtils> { PlatformUtilsImpl(androidContext()) }
}
