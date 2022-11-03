package pini.mattia.coachtimer

import org.junit.Assert.assertTrue
import org.junit.Test
import pini.mattia.coachtimer.model.player.Picture
import pini.mattia.coachtimer.model.player.Player
import pini.mattia.coachtimer.model.trainingsession.Lap
import pini.mattia.coachtimer.model.trainingsession.TrainingSession
import java.time.LocalDateTime
import kotlin.math.roundToInt

class TrainingSessionTest {
    private val trainingSessionUnderTest = TrainingSession(
        LocalDateTime.now(),
        30,
        Player("Mr", "Sir", "Pounce", Picture("fake", "fake", "fake")),
        listOf(
            Lap(5000),
            Lap(5500),
            Lap(6000),
            Lap(6500),
            Lap(7000)
        )
    )

    @Test
    fun calculate_average_time_correct() {
        assertTrue(trainingSessionUnderTest.calculateAverageLapTime() == 6000)
    }

    @Test
    fun get_number_of_laps_correct() {
        assertTrue(trainingSessionUnderTest.getNumberOfLaps() == 5)
    }

    @Test
    fun calculate_average_speed_correct() {
        // the real result would be 5.07 and other decimals, rounded to int for simplicity of test.
        val averageSpeed = trainingSessionUnderTest.calculateAverageSpeed()
        assertTrue(averageSpeed.roundToInt() == 5)
    }

    @Test
    fun calculate_max_speed_correct() {
        val maxSpeed = trainingSessionUnderTest.calculateMaxSpeed()
        assertTrue(maxSpeed == 6.0)
    }
}
