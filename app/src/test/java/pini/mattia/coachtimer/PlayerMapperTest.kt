package pini.mattia.coachtimer

import org.junit.Assert.assertTrue
import org.junit.Test
import pini.mattia.coachtimer.data.player.PictureDTO
import pini.mattia.coachtimer.data.player.PlayerDTO
import pini.mattia.coachtimer.data.player.PlayerDTOMapper
import pini.mattia.coachtimer.data.player.PlayerMapper
import pini.mattia.coachtimer.data.player.PlayerName
import pini.mattia.coachtimer.model.player.Picture
import pini.mattia.coachtimer.model.player.Player

class PlayerMapperTest {

    private val playerMapper = PlayerMapper()
    private val playerDTOMapper = PlayerDTOMapper()

    @Test
    fun player_mapper_mapping_succeed() {
        val playerDTO = PlayerDTO(
            name = PlayerName("Miss", "Kappa", "U'rina"),
            picture = PictureDTO("fakeImg1", "fakeImg2", "fakeImg3")
        )

        val player = playerMapper.mapTo(playerDTO)

        assertTrue(player.title == "Miss")
        assertTrue(player.name == "Kappa")
        assertTrue(player.surname == "U'rina")
        assertTrue(player.picture.thumbnail == "fakeImg1")
        assertTrue(player.picture.large == "fakeImg2")
        assertTrue(player.picture.medium == "fakeImg3")
    }

    @Test
    fun player_mapper_dto_mapping_succeed() {
        val player = Player(
            "Miss",
            "Kappa",
            "U'rina",
            picture = Picture("fakeImg1", "fakeImg2", "fakeImg3")
        )

        val playerDTO = playerDTOMapper.mapTo(player)

        assertTrue(playerDTO.name.title == "Miss")
        assertTrue(playerDTO.name.first == "Kappa")
        assertTrue(playerDTO.name.last == "U'rina")
        assertTrue(playerDTO.picture.thumbnail == "fakeImg1")
        assertTrue(playerDTO.picture.large == "fakeImg2")
        assertTrue(playerDTO.picture.medium == "fakeImg3")
    }
}
