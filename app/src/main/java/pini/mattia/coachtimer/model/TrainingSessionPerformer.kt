package pini.mattia.coachtimer.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pini.mattia.coachtimer.model.player.Player
import pini.mattia.coachtimer.model.trainingsession.Lap
import pini.mattia.coachtimer.model.trainingsession.TrainingSession
import java.util.Date

/**
 * Class responsible of the stopwatch and the session
 * By updating the training session we can keep the current session with real time performances
 * The trick here is to keep a second timer between laps to calculate directly the time of each lap
 * The session can last at least 90 minutes
 */
class TrainingSessionPerformer(
    player: Player,
    lapDistance: Int
) {
    private val _onGoingTrainingSession = MutableStateFlow(
        TrainingSession(
            Date().time,
            0,
            lapDistance,
            player,
            emptyList()
        )
    )
    val onGoingTrainingSession: StateFlow<TrainingSession>
        get() = _onGoingTrainingSession

    private var started: Boolean = false
    private var lapTimeCounter = 0

    private lateinit var watchJob: Job

    fun start(coroutineScope: CoroutineScope) {
        if (!started) {
            started = true
            watchJob = coroutineScope.launch {
                while (_onGoingTrainingSession.value.totalElapsedTime < MAX_TIMER_TIME)
                    _onGoingTrainingSession.emit(
                        _onGoingTrainingSession.value.copy(
                            totalElapsedTime = _onGoingTrainingSession.value.totalElapsedTime + 1
                        )
                    )
                lapTimeCounter += 1
                delay(1)
            }
        }
    }

    fun addLap() {
        val currentLapTime = lapTimeCounter
        val trainingSessions = _onGoingTrainingSession.value
        val laps = trainingSessions.laps.toMutableList().apply { add(Lap(currentLapTime)) }
        _onGoingTrainingSession.tryEmit(
            _onGoingTrainingSession.value.copy(
                laps = laps
            )
        )
        lapTimeCounter = 0
    }

    fun stop() {
        if (started) {
            watchJob.cancel()
        }
    }

    companion object {
        // max timer is 90 minutes
        private const val MAX_TIMER_TIME = 90 * 60 * 1000
    }
}
