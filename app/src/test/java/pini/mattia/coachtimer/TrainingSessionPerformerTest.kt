package pini.mattia.coachtimer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import pini.mattia.coachtimer.model.TimeRetriever
import pini.mattia.coachtimer.model.TrainingSessionPerformer

@OptIn(ExperimentalCoroutinesApi::class)
class TrainingSessionPerformerTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var trainingSessionPerformer: TrainingSessionPerformer
    private val timeRetriever: TimeRetriever = mock(TimeRetriever::class.java)

    @Before
    fun prepareTrainingSessionPerformer() {
        val player = fakePlayers.first()
        val lapDistance = 30
        `when`(timeRetriever.getCurrentTimeMillis()).thenReturn(15000L)
        trainingSessionPerformer = TrainingSessionPerformer(timeRetriever)
        trainingSessionPerformer.initializeSession(player, lapDistance)
    }

    @Test
    fun when_init_correct_training_session() = runTest {
        assert(trainingSessionPerformer.onGoingTrainingSession.value?.totalElapsedTime == 0L)
        assert(trainingSessionPerformer.onGoingTrainingSession.value?.laps?.isEmpty() == true)
        assert(trainingSessionPerformer.onGoingTrainingSession.value?.lapDistance == 30)
        assert(trainingSessionPerformer.onGoingTrainingSession.value?.player == fakePlayers.first())
    }

    @Test
    fun when_session_started_time_elapses() = runTest {
        trainingSessionPerformer.start(this)
        delay(200)
        // since time is mocked constant, now it is always 0
        assert(trainingSessionPerformer.onGoingTrainingSession.value?.totalElapsedTime == 0L)
        trainingSessionPerformer.stop()
    }

    @Test
    fun when_addLap_lap_added() = runTest {
        trainingSessionPerformer.start(this)

        trainingSessionPerformer.addLap()
        trainingSessionPerformer.addLap()
        assert(trainingSessionPerformer.onGoingTrainingSession.value?.laps?.size == 2)
        trainingSessionPerformer.stop()
    }
}
