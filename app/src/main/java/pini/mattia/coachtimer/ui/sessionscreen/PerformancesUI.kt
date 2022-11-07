package pini.mattia.coachtimer.ui.sessionscreen

data class PerformancesUI(
    val numberOfLaps: String,
    val maxSpeed: String,
    val lapsFormatted: List<String>,
    val laps: List<Long>,
    val averageSpeed: String,
    val averageLapTimeFormatted: String,
    val averageLapTime: Long
)
