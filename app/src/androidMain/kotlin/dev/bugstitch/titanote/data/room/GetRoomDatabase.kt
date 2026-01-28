package dev.bugstitch.titanote.data.room

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.bugstitch.titanote.utils.Constants

fun getNotesDatabase(context: Context): NotesDatabase {
    val dbFile = context.getDatabasePath(Constants.DATABASE_NAME)

    return Room.databaseBuilder<NotesDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .addMigrations(Migration_3_4)
        .build()
}
