package pini.mattia.coachtimer.ui.leaderboardscreen

import pini.mattia.coachtimer.model.player.Player

data class TrainingSessionUI(
    val player: Player,
    val peakSpeed: String,
    val endurance: String
)
