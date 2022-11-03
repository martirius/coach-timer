package pini.mattia.coachtimer

import org.junit.Assert.assertTrue
import org.junit.Test
import pini.mattia.coachtimer.data.player.PictureDTO
import pini.mattia.coachtimer.data.player.PlayerDTO
import pini.mattia.coachtimer.data.player.PlayerMapper
import pini.mattia.coachtimer.data.player.PlayerName

class PlayerMapperTest {

    private val playerMapper = PlayerMapper()

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
}
