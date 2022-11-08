package pini.mattia.coachtimer.data.player

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class PlayersResponse(
    val results: List<PlayerDTO>
)

@Serializable
@Entity(tableName = "player")
data class PlayerDTO(
    @Embedded
    @PrimaryKey
    val name: PlayerName,
    @Embedded
    val picture: PictureDTO
)

@Serializable
data class PlayerName(
    val title: String,
    val first: String,
    val last: String
)

@Serializable
data class PictureDTO(
    val thumbnail: String,
    val large: String,
    val medium: String
)
