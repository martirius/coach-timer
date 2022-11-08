package pini.mattia.coachtimer.ui.leaderboardscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pini.mattia.coachtimer.model.trainingsession.TrainingSession
import pini.mattia.coachtimer.model.trainingsession.TrainingSessionFilter
import pini.mattia.coachtimer.model.trainingsession.TrainingSessionRepository
import pini.mattia.coachtimer.ui.mainscreen.MainScreenViewModel
import javax.inject.Inject

@HiltViewModel
class LeaderBoardScreenViewModel @Inject constructor(
    private val trainingSessionRepository: TrainingSessionRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(
        ViewState(status = MainScreenViewModel.Status.LOADING, selectedFilter = TrainingSessionFilter.PEAK_SPEED)
    )
    val viewState: StateFlow<ViewState>
        get() = _viewState

    private val trainingSessions: MutableList<TrainingSession> = mutableListOf()

    init {
        loadLeaderboard()
    }

    private fun loadLeaderboard() {
        viewModelScope.launch {
            trainingSessionRepository.getTrainingSessions().collect {
                trainingSessions.clear()
                trainingSessions.addAll(it)
                _viewState.emit(
                    ViewState(
                        trainingSessions = applyFilterToTrainingSession(_viewState.value.selectedFilter, it)
                            .map { trainingSession -> mapTrainingSessionToUI(trainingSession) },
                        status = MainScreenViewModel.Status.SUCCESS
                    )
                )
            }
        }
    }

    fun setFilter(filter: TrainingSessionFilter) {
        _viewState.tryEmit(
            ViewState(
                selectedFilter = filter,
                trainingSessions = applyFilterToTrainingSession(filter, trainingSessions)
                    .map { trainingSession -> mapTrainingSessionToUI(trainingSession) },
                status = MainScreenViewModel.Status.SUCCESS
            )
        )
    }

    private fun applyFilterToTrainingSession(
        filter: TrainingSessionFilter,
        trainingSessions: List<TrainingSession>
    ): List<TrainingSession> {
        return when (filter) {
            TrainingSessionFilter.PEAK_SPEED -> trainingSessions.sortedByDescending { trainingSession ->
                trainingSession.calculateMaxSpeed()
            }
            TrainingSessionFilter.ENDURANCE -> trainingSessions.sortedByDescending { trainingSession ->
                trainingSession.getNumberOfLaps()
            }
        }
    }

    private fun mapTrainingSessionToUI(trainingSession: TrainingSession): TrainingSessionUI {
        return TrainingSessionUI(
            trainingSession.player,
            endurance = trainingSession.laps.size.toString(),
            peakSpeed = "%.2f m/s".format(trainingSession.calculateMaxSpeed())
        )
    }

    data class ViewState(
        val status: MainScreenViewModel.Status,
        val selectedFilter: TrainingSessionFilter = TrainingSessionFilter.PEAK_SPEED,
        val trainingSessions: List<TrainingSessionUI> = emptyList()
    )
}
