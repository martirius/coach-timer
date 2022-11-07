package pini.mattia.coachtimer.ui.sessionscreen

import pini.mattia.coachtimer.model.player.Player

data class TrainingSessionUI(
    val player: Player,
    val formattedElapsedTime: String,
    val isSessionStarted: Boolean,
    val performancesUI: PerformancesUI
)
