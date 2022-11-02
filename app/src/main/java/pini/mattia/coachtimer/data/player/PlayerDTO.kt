package pini.mattia.coachtimer.data.player
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDTO(
    val name: PlayerName,
    val picture: PictureDTO
)

@Serializable
data class PlayerName(
    val title: String,
    val first: String,
    val second: String
)

@Serializable
data class PictureDTO(
    val thumbnail: String,
    val large: String,
    val medium: String
)
