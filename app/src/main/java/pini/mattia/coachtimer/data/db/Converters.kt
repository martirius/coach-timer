package pini.mattia.coachtimer.data.db

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Convert a List<Long> to a supported Room type
 */
class Converters {
    @TypeConverter
    fun fromList(value: List<Long>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<Long>>(value)
}
