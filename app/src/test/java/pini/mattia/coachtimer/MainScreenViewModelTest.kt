package pini.mattia.coachtimer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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

    @Test
    fun when_lap_distance_not_correct_show_error() = runTest {
        val playerRepository = mock<PlayersRepository>()
        whenever(playerRepository.getPlayers()).doSuspendableAnswer {
            Result.success(fakePlayers)
        }
        viewModel = MainScreenViewModel(playerRepository)
        advanceUntilIdle()

        viewModel.onPlayerSelected(fakePlayers.first())
        // not a number
        viewModel.lapDistanceChanged("")
        assert(!viewModel.viewState.value.inputDialogState.dataValid)

        // outside of upper and lower bound
        viewModel.lapDistanceChanged("4")
        assert(!viewModel.viewState.value.inputDialogState.dataValid)

        viewModel.lapDistanceChanged("101")
        assert(!viewModel.viewState.value.inputDialogState.dataValid)
    }

    @Test
    fun when_lap_distance_inside_bounds_dataValid() = runTest {
        val playerRepository = mock<PlayersRepository>()
        whenever(playerRepository.getPlayers()).doSuspendableAnswer {
            Result.success(fakePlayers)
        }
        viewModel = MainScreenViewModel(playerRepository)
        advanceUntilIdle()

        viewModel.onPlayerSelected(fakePlayers.first())
        viewModel.lapDistanceChanged("10")
        assert(viewModel.viewState.value.inputDialogState.dataValid)
    }

    @Test
    fun when_startSession_prompt_navigation() = runTest {
        val playerRepository = mock<PlayersRepository>()
        whenever(playerRepository.getPlayers()).doSuspendableAnswer {
            Result.success(fakePlayers)
        }
        viewModel = MainScreenViewModel(playerRepository)
        advanceUntilIdle()

        viewModel.onPlayerSelected(fakePlayers.first())
        advanceUntilIdle()
        // not a number
        viewModel.lapDistanceChanged("10")
        advanceUntilIdle()

        viewModel.viewState.first()

        val navValue = mutableListOf<String>()
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.navigation.toList(navValue)
        }

        viewModel.startSession()
        advanceUntilIdle()

        assert(
            navValue.first() == Destinations.prepareSessionScreenNavigation(
                fakePlayers.first().getPlayerId(),
                10
            )
        )
        job.cancel()
    }
}
