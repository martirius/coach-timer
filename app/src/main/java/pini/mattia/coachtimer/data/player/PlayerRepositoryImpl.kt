package pini.mattia.coachtimer.data.player

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pini.mattia.coachtimer.model.player.Player
import pini.mattia.coachtimer.model.player.PlayersRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerService: PlayerService,
    private val playerMapper: PlayerMapper,
    private val playerDao: PlayerDao
) : PlayersRepository {
    override suspend fun getPlayers(): Result<List<Player>> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val playersSaved = playerDao.getPlayers()
                if (playersSaved.isEmpty()) {
                    playerService.getPlayers().getOrThrow().results
                        .also {
                            // save players to dao
                            // in a real situation we should check for fresh data every time
                            playerDao.addPlayers(it)
                        }.map { playerDto ->
                            playerMapper.mapTo(playerDto)
                        }
                } else {
                    playersSaved.map { playerDto ->
                        playerMapper.mapTo(playerDto)
                    }
                }
            }
        }
    }
}
