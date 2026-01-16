package dev.bugstitch.titanote.utils

import androidx.room.TypeConverter
import kotlin.time.Instant

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.fromEpochMilliseconds(it) }
    }

    @TypeConverter
    fun instantToTimestamp(instant: Instant?): Long? {
        return instant?.toEpochMilliseconds()
    }
}
