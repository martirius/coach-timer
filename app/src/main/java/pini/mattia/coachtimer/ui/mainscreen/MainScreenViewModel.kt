package pini.mattia.coachtimer.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pini.mattia.coachtimer.model.player.Player
import pini.mattia.coachtimer.model.player.PlayersRepository
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(ViewState(status = Status.LOADING))
    val viewState: StateFlow<ViewState>
        get() = _viewState

    private val _navigation = MutableSharedFlow<String>()
    val navigation: SharedFlow<String>
        get() = _navigation

    init {
        loadPlayers()
    }

    private fun loadPlayers() {
        viewModelScope.launch {
            kotlin.runCatching {
                playersRepository.getPlayers().getOrThrow()
            }.fold(
                onSuccess = {
                    _viewState.emit(ViewState(players = it, status = Status.SUCCESS))
                },
                onFailure = {
                    _viewState.emit(ViewState(status = Status.FAILED))
                }
            )
        }
    }

    fun onPlayerSelected(player: Player) {
        viewModelScope.launch {
            _viewState.emit(
                viewState.value.copy(
                    selectedPlayer = if (viewState.value.selectedPlayer == player) {
                        null
                    } else player
                )
            )
        }
    }

    fun openInputDialog() {
        viewModelScope.launch {
            _viewState.emit(
                viewState.value.copy(
                    inputDialogState = viewState.value.inputDialogState.copy(showInputDialog = true)
                )
            )
        }
    }

    fun closeInputDialogPressed() {
        viewModelScope.launch {
            _viewState.emit(
                viewState.value.copy(
                    inputDialogState = ViewState.InputDialogState()
                )
            )
        }
    }

    fun lapDistanceChanged(lapDistance: String) {
        _viewState.tryEmit(
            viewState.value.copy(
                inputDialogState = viewState.value.inputDialogState.copy(
                    sessionLapDistance = lapDistance,
                    // if data is not in interval 5 - 100, or data is not correct, dataValid is set to false
                    dataValid = try {
                        lapDistance.toInt() in MIN_LAP_DISTANCE..MAX_LAP_DISTANCE
                    } catch (e: NumberFormatException) {
                        false
                    }
                )
            )
        )
    }

    data class ViewState(
        val status: Status,
        val players: List<Player> = emptyList(),
        val selectedPlayer: Player? = null,
        val inputDialogState: InputDialogState = InputDialogState()
    ) {
        data class InputDialogState(
            val showInputDialog: Boolean = false,
            val sessionLapDistance: String = "5",
            val dataValid: Boolean = true
        )
    }
    enum class Status {
        LOADING,
        FAILED,
        SUCCESS
    }

    companion object {
        private const val MIN_LAP_DISTANCE = 5
        private const val MAX_LAP_DISTANCE = 100
    }
}
