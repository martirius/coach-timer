package pini.mattia.coachtimer.model.player

interface PlayersRepository {

    /**
     * Return a list of random [Player]s wrapped in a [kotlin.Result]
     */
    suspend fun getPlayers(): Result<List<Player>>
}
