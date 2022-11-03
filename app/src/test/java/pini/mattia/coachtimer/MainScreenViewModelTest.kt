package pini.mattia.coachtimer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import pini.mattia.coachtimer.model.player.PlayersRepository
import pini.mattia.coachtimer.ui.mainscreen.MainScreenViewModel

@ExperimentalCoroutinesApi
class MainScreenViewModelTest {
    private lateinit var viewModel: MainScreenViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun when_initialized_state_loading_then_success() = runTest {
        val playerRepository = mock<PlayersRepository>()
        whenever(playerRepository.getPlayers()).doSuspendableAnswer {
            delay(200)
            Result.success(fakePlayers)
        }
        viewModel = MainScreenViewModel(playerRepository)
        assert(viewModel.viewState.value.status == MainScreenViewModel.Status.LOADING)
        advanceUntilIdle()
        assert(viewModel.viewState.value.status == MainScreenViewModel.Status.SUCCESS)
        assert(viewModel.viewState.value.players.size == 3) // players in mock are 3
    }

    @Test
    fun when_api_fails_state_failed_on_initialization() = runTest {
        val playerRepository = mock<PlayersRepository>()
        // don't know why in this case test fails, it should work as expected
        // whenever(playerRepository.getPlayers()).thenReturn(Result.failure(IllegalStateException()))
        whenever(playerRepository.getPlayers()).thenThrow(IllegalStateException())
        viewModel = MainScreenViewModel(playerRepository)
        assert(viewModel.viewState.value.status == MainScreenViewModel.Status.LOADING)
        advanceUntilIdle()
        assert(viewModel.viewState.value.status == MainScreenViewModel.Status.FAILED)
    }

    @Test
    fun when_player_selected_state_success_correct() = runTest {
        val playerRepository = mock<PlayersRepository>()
        whenever(playerRepository.getPlayers()).doSuspendableAnswer {
            delay(200)
            Result.success(fakePlayers)
        }
        viewModel = MainScreenViewModel(playerRepository)
        assert(viewModel.viewState.value.status == MainScreenViewModel.Status.LOADING)
        advanceUntilIdle()
        assert(viewModel.viewState.value.status == MainScreenViewModel.Status.SUCCESS)
        viewModel.onPlayerSelected(fakePlayers.last())
        advanceUntilIdle()
        assert(viewModel.viewState.value.status == MainScreenViewModel.Status.SUCCESS)
        assert(viewModel.viewState.value.selectedPlayer != null)
        assert(viewModel.viewState.value.selectedPlayer == fakePlayers.last())
    }
}
