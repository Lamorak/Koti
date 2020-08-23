package cz.lamorak.koti.database

import androidx.room.TypeConverter
import java.time.Instant

class Converters {

    @TypeConverter
    fun instantToMillis(instant: Instant) = instant.toEpochMilli()

    @TypeConverter
    fun millisToInstant(millis: Long) = Instant.ofEpochMilli(millis)
}