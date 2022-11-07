package pini.mattia.coachtimer

object Destinations {
    const val MAIN_SCREEN = "main"
    const val SESSION_SCREEN = "session/{playerId}/{lapDistance}"
    const val LEADERBOARD_SCREEN = "leaderboard"

    fun prepareSessionScreenNavigation(playerId: String, lapDistance: Int): String {
        return SESSION_SCREEN
            .replace("{playerId}", playerId)
            .replace("{lapDistance}", lapDistance.toString())
    }
}
