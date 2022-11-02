package pini.mattia.coachtimer.data.player

import pini.mattia.coachtimer.model.player.Player
import pini.mattia.coachtimer.model.player.PlayersRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerService: PlayerService,
    private val playerMapper: PlayerMapper
) : PlayersRepository {
    override suspend fun getPlayers(): Result<List<Player>> {
        val players = playerService.getPlayers().getOrNull()
        return players?.let {
            Result.success(
                players.map { playerDto -> playerMapper.mapTo(playerDto) }
            )
        } ?: Result.failure(RuntimeException())
    }
}
