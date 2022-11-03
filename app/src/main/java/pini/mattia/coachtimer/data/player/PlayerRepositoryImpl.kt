package pini.mattia.coachtimer.data.player

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pini.mattia.coachtimer.model.player.Player
import pini.mattia.coachtimer.model.player.PlayersRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerService: PlayerService,
    private val playerMapper: PlayerMapper
) : PlayersRepository {
    override suspend fun getPlayers(): Result<List<Player>> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val playersResponse = playerService.getPlayers().getOrThrow()
                playersResponse.results.map { playerDto -> playerMapper.mapTo(playerDto) }
            }
        }
    }
}
