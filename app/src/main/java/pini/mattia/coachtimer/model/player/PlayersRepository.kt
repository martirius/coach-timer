package pini.mattia.coachtimer.model.player

interface PlayersRepository {

    /**
     * Return a list of random players
     */
    suspend fun getPlayers(): List<Player>
}
