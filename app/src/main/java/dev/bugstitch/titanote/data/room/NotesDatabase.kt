package dev.bugstitch.titanote.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.utils.Constants

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object{

        @Volatile
        private var Instance:NotesDatabase? = null

        fun getDatabase(context: Context):NotesDatabase{
           return Instance?: synchronized(this){
                Room.databaseBuilder(context = context,
                    NotesDatabase::class.java,
                    name = Constants.DATABASE_NAME)
                    .build()
                    .also { Instance = it }
            }
        }

    }

}