package pini.mattia.coachtimer.ui.sessionscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import pini.mattia.coachtimer.model.TrainingSessionPerformer
import pini.mattia.coachtimer.model.player.Player
import pini.mattia.coachtimer.model.player.PlayersRepository
import pini.mattia.coachtimer.model.trainingsession.TrainingSession
import pini.mattia.coachtimer.model.trainingsession.TrainingSessionRepository
import pini.mattia.coachtimer.ui.mainscreen.MainScreenViewModel
import javax.inject.Inject

@HiltViewModel
class SessionScreenViewModel @Inject constructor(
    private val playersRepository: PlayersRepository,
    private val trainingSessionRepository: TrainingSessionRepository,
    private val trainingSessionPerformer: TrainingSessionPerformer,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = MutableStateFlow(ViewState(MainScreenViewModel.Status.LOADING))

    val viewState: StateFlow<ViewState>
        get() = _viewState

    init {
        val lapDistance: Int? = savedStateHandle["lapDistance"]
        val playerId: String? = savedStateHandle["playerId"]
        // check if data comes correctly from navigation
        if (playerId != null && lapDistance != null) {
            viewModelScope.launch {
                initializeTrainingSession(playerId, lapDistance)
                trainingSessionPerformer.onGoingTrainingSession.filterNotNull().collect { trainingSession ->
                    val trainingSessionUI = TrainingSessionUI(
                        trainingSession.player,
                        formatElapsedTime(trainingSession.totalElapsedTime),
                        trainingSession.totalElapsedTime > 0,
                        performancesUI = mapTrainingSessionToPerformanceUI(trainingSession)
                    )
                    _viewState.emit(
                        _viewState.value.copy(
                            status = MainScreenViewModel.Status.SUCCESS,
                            trainingSession = trainingSessionUI
                        )
                    )
                }
            }
        } else {
            // somehow data didn't arrive, show error
        }
    }

    private fun formatElapsedTime(millis: Long): String {
        val minutes = millis / 1000 / 60
        val seconds = (millis / 1000) % 60
        return "%02d' %02d\".%03d".format(
            minutes, // milliseconds to minuts
            seconds, // milliseconds to seconds
            millis and 999
        )
    }

    private suspend fun initializeTrainingSession(playerId: String, lapDistance: Int) {
        val player = getPlayerWithId(playerId)
        trainingSessionPerformer.initializeSession(player, lapDistance)
    }

    private suspend fun getPlayerWithId(playerId: String): Player {
        return playersRepository.getPlayers().getOrThrow().first { it.getPlayerId() == playerId }
    }

    private fun mapTrainingSessionToPerformanceUI(trainingSession: TrainingSession): PerformancesUI {
        return PerformancesUI(
            numberOfLaps = trainingSession.laps.size.toString(),
            maxSpeed = "%.2f m/s".format(trainingSession.calculateMaxSpeed()),
            lapsFormatted = trainingSession.laps.mapIndexed { index, lap ->
                "%02d: ${lap.lapTime}".format(index + 1)
            },
            laps = trainingSession.laps.map { it.lapTime },
            averageSpeed = "%.2f m/s".format(trainingSession.calculateAverageSpeed()),
            averageLapTimeFormatted = formatElapsedTime(trainingSession.calculateAverageLapTime()),
            averageLapTime = trainingSession.calculateAverageLapTime()
        )
    }

    fun startSession() {
        trainingSessionPerformer.start(viewModelScope)
    }

    fun addLap() {
        trainingSessionPerformer.addLap()
    }

    fun stopSession() {
        trainingSessionPerformer.stop()
    }

    fun saveSession() {
        if (trainingSessionPerformer.sessionStatus == TrainingSessionPerformer.SessionStatus.PERFORMING ||
            trainingSessionPerformer.sessionStatus == TrainingSessionPerformer.SessionStatus.STOPPED
        ) {
            trainingSessionPerformer.stop()
            viewModelScope.launch {
                trainingSessionPerformer.onGoingTrainingSession.value?.let {
                    trainingSessionRepository.addTrainingSession(
                        it
                    )
                }
            }
        }
    }

    data class ViewState(
        val status: MainScreenViewModel.Status,
        val trainingSession: TrainingSessionUI? = null
    )
}
