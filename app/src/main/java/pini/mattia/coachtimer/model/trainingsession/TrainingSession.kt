package pini.mattia.coachtimer.model.trainingsession

import pini.mattia.coachtimer.model.player.Player

/**
 * Class representing a terminated training session
 * It exposes also method to retrieve useful metrics about the session
 */
data class TrainingSession(
    val sessionDateMillis: Long,
    val totalElapsedTime: Long,
    val lapDistance: Int,
    val player: Player,
    val laps: List<Lap>
) {
    fun getNumberOfLaps(): Int = laps.size

    /**
     * Return average time expressed in milliseconds
     */
    fun calculateAverageLapTime(): Long = if (laps.isNotEmpty()) laps.sumOf { it.lapTime } / laps.size else 0

    /**
     *  Return the average speed expressed in m/s
     */
    fun calculateAverageSpeed(): Double = if (laps.isNotEmpty()) laps.sumOf {
        calculateSpeed(
            lapDistance,
            it.lapTime.toDouble() / 1000
        )
    } / laps.size else 0.0

    /**
     * Returns max speed of the session
     */
    fun calculateMaxSpeed(): Double {
        return laps.maxOfOrNull {
            calculateSpeed(
                lapDistance,
                it.lapTime.toDouble() / 1000
            )
        } ?: 0.0
    }

    private fun calculateSpeed(distance: Int, timeInSeconds: Double): Double = distance / timeInSeconds
}

data class Lap(val lapTime: Long)
