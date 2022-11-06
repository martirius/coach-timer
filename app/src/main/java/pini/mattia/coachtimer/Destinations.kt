package pini.mattia.coachtimer

object Destinations {
    const val MAIN_SCREEN = "main"
    const val SESSION_SCREEN = "session/{userId}/{lapDistance}"
    const val LEADERBOARD_SCREEN = "leaderboard"

    fun prepareSessionScreenNavigation(userId: String, lapDistance: Int): String {
        return SESSION_SCREEN
            .replace("{userId}", userId)
            .replace("{lapDistance}", lapDistance.toString())
    }
}
