package pini.mattia.coachtimer.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
            _viewState.emit(viewState.value.copy(selectedPlayer = player))
        }
    }

    data class ViewState(
        val status: Status,
        val players: List<Player> = emptyList(),
        val selectedPlayer: Player? = null
    )
    enum class Status {
        LOADING,
        FAILED,
        SUCCESS
    }
}
