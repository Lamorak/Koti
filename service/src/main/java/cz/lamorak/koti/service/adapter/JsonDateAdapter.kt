package cz.lamorak.koti.service.adapter

import com.squareup.moshi.*
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class JsonDateAdapter {

    @FromJson
    fun fromJson(date: String): Instant? = try {
        val instant = Instant.parse(date)
        instant
    } catch (e: DateTimeParseException) {
        null
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Instant?) {
        value?.run {
            writer.value(DateTimeFormatter.ISO_DATE.format(value))
        }
    }
}