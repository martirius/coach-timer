package pini.mattia.coachtimer.model.player

/**
 * Data class representing an athlete
 */
data class Player(
    val title: String,
    val name: String,
    val surname: String,
    val picture: Picture
) {
    fun getPlayerId() = "${title}_${name}_$surname"
}

data class Picture(
    val thumbnail: String,
    val large: String,
    val medium: String
)
