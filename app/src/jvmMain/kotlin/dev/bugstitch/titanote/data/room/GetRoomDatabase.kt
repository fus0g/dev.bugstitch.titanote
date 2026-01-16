package dev.bugstitch.titanote.data.room

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.room.Room
import java.io.File

fun getNotesDatabase(): NotesDatabase {
    val appDir = File(
        System.getProperty("user.home"),
        ".titanote"
    ).apply { mkdirs() }

    val dbFile = File(appDir, "notes.db")

    return Room.databaseBuilder<NotesDatabase>(
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}
