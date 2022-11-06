package pini.mattia.coachtimer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import pini.mattia.coachtimer.model.TrainingSessionPerformer

@OptIn(ExperimentalCoroutinesApi::class)
class TrainingSessionPerformerTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun when_init_correct_training_session() = runTest {
        val player = fakePlayers.first()
        val lapDistance = 30
        val trainingSessionPerformer = TrainingSessionPerformer(player, lapDistance)

        assert(trainingSessionPerformer.onGoingTrainingSession.value.totalElapsedTime == 0L)
        assert(trainingSessionPerformer.onGoingTrainingSession.value.laps.isEmpty())
        assert(trainingSessionPerformer.onGoingTrainingSession.value.lapDistance == 30)
        assert(trainingSessionPerformer.onGoingTrainingSession.value.player == player)
    }

    @Test
    fun when_session_started_time_elapses() = runTest {
        val player = fakePlayers.first()
        val lapDistance = 30
        val trainingSessionPerformer = TrainingSessionPerformer(player, lapDistance)
        trainingSessionPerformer.start(this)
        delay(200)
        assert(trainingSessionPerformer.onGoingTrainingSession.value.totalElapsedTime >= 200)
        trainingSessionPerformer.stop()
    }

    @Test
    fun when_addLap_lap_added() = runTest {
        val player = fakePlayers.first()
        val lapDistance = 30
        val trainingSessionPerformer = TrainingSessionPerformer(player, lapDistance)
        trainingSessionPerformer.start(this)

        trainingSessionPerformer.addLap()
        trainingSessionPerformer.addLap()
        assert(trainingSessionPerformer.onGoingTrainingSession.value.laps.size == 2)
        trainingSessionPerformer.stop()
    }
}
