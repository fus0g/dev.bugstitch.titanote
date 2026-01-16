package dev.bugstitch.titanote.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.bugstitch.titanote.utils.Constants
import dev.bugstitch.titanote.utils.DateConverter
import kotlin.time.Instant

@Entity(tableName = Constants.TABLE_NAME)
@TypeConverters(DateConverter::class)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var title:String,
    var content:String,
    var date: Instant,
    val color:Int,
    val logo:Int,
)

