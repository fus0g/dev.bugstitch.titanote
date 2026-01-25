package dev.bugstitch.titanote.utils

import androidx.room.TypeConverter
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
fun Instant.formatForUi(): String {
    val local = toLocalDateTime(TimeZone.currentSystemDefault())

    fun two(value: Int) = value.toString().padStart(2, '0')

    val hour12 = when {
        local.hour == 0 -> 12
        local.hour > 12 -> local.hour - 12
        else -> local.hour
    }

    val amPm = if (local.hour < 12) "AM" else "PM"

    return "${two(local.dayOfMonth)}/${two(local.monthNumber)}/${local.year} " +
            "at ${two(hour12)}:${two(local.minute)} $amPm"
}

object DateConverterObject{
    fun fromTimestamp(value: Long?): Instant {
        return value?.let { Instant.fromEpochMilliseconds(it) }?: Instant.fromEpochMilliseconds(0)
    }
    fun instantToTimestamp(instant: Instant?): Long {
        return instant?.toEpochMilliseconds() ?: 0
    }
}
