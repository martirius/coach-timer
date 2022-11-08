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
import javax.inject.Inject

/**
 * Class responsible of the stopwatch and the session
 * By updating the training session we can keep the current session with real time performances
 * The trick here is to keep a second timer between laps to calculate directly the time of each lap
 * The session can last at least 90 minutes
 */
class TrainingSessionPerformer @Inject constructor(
    private val timeRetriever: TimeRetriever
) {
    private val _onGoingTrainingSession: MutableStateFlow<TrainingSession?> = MutableStateFlow(
        null
    )
    val onGoingTrainingSession: StateFlow<TrainingSession?>
        get() = _onGoingTrainingSession

    private var lastLapMillis = 0L

    private lateinit var watchJob: Job
    var sessionStatus = SessionStatus.UNITIALIZED
        private set

    fun initializeSession(
        player: Player,
        lapDistance: Int
    ) {
        _onGoingTrainingSession.tryEmit(
            TrainingSession(
                timeRetriever.getCurrentTimeMillis(),
                0,
                lapDistance,
                player,
                emptyList()
            )
        )
        sessionStatus = SessionStatus.INITIALIZED
    }

    fun start(coroutineScope: CoroutineScope) {
        if (sessionStatus == SessionStatus.INITIALIZED) {
            sessionStatus = SessionStatus.PERFORMING
            watchJob = coroutineScope.launch {
                val startedTimeMillis: Long = timeRetriever.getCurrentTimeMillis()
                lastLapMillis = startedTimeMillis
                while (timeRetriever.getCurrentTimeMillis() - startedTimeMillis < MAX_TIMER_TIME) {
                    _onGoingTrainingSession.tryEmit(
                        _onGoingTrainingSession.value?.copy(
                            totalElapsedTime = timeRetriever.getCurrentTimeMillis() - startedTimeMillis
                        )
                    )
                    delay(1) // let context switch
                }
            }
        }
    }

    fun addLap() {
        if (sessionStatus == SessionStatus.PERFORMING) {
            _onGoingTrainingSession.value?.let { trainingSession ->
                val currentLapTime = timeRetriever.getCurrentTimeMillis() - lastLapMillis
                lastLapMillis = timeRetriever.getCurrentTimeMillis()
                val laps = trainingSession.laps.toMutableList().apply { add(Lap(currentLapTime)) }
                _onGoingTrainingSession.tryEmit(
                    trainingSession.copy(
                        laps = laps
                    )
                )
            }
        }
    }

    fun stop() {
        if (sessionStatus == SessionStatus.PERFORMING) {
            sessionStatus = SessionStatus.STOPPED
            watchJob.cancel()
        }
    }

    companion object {
        // max timer is 90 minutes
        private const val MAX_TIMER_TIME = 90 * 60 * 1000
    }

    enum class SessionStatus {
        UNITIALIZED,
        INITIALIZED,
        PERFORMING,
        STOPPED
    }
}
