package pini.mattia.coachtimer.data.player

import pini.mattia.coachtimer.model.Mapper
import pini.mattia.coachtimer.model.player.Picture
import pini.mattia.coachtimer.model.player.Player
import javax.inject.Inject

/**
 * This class map the Player DTO to Player domain class
 */
class PlayerMapper @Inject constructor() : Mapper<Player, PlayerDTO> {
    override fun mapTo(objectToMap: PlayerDTO): Player {
        return Player(
            objectToMap.name.title,
            objectToMap.name.first,
            objectToMap.name.last,
            Picture(
                objectToMap.picture.thumbnail,
                objectToMap.picture.large,
                objectToMap.picture.medium
            )
        )
    }
}
