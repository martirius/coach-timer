package pini.mattia.coachtimer.ui.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import pini.mattia.coachtimer.R
import pini.mattia.coachtimer.model.player.Picture
import pini.mattia.coachtimer.model.player.Player
import pini.mattia.coachtimer.ui.theme.CoachTimerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = hiltViewModel()
    // waiting for collectAsStateWithLifecycle to comes out of alpha
    val viewState by viewModel.viewState.collectAsState()

    CoachTimerTheme {
        Scaffold(topBar = {
            Box(modifier = Modifier.padding(all = 24.dp)) {
                Text(text = "Players", fontSize = 32.sp)
            }
        }) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (viewState.status) {
                    MainScreenViewModel.Status.LOADING -> {
                    }
                    MainScreenViewModel.Status.FAILED -> {
                    }
                    MainScreenViewModel.Status.SUCCESS -> PlayersList(
                        players = viewState.players,
                        selectedPlayer = viewState.selectedPlayer,
                        onPlayerSelected = viewModel::onPlayerSelected
                    )
                }
            }
        }
    }
}

@Composable
fun PlayersList(players: List<Player>, selectedPlayer: Player?, onPlayerSelected: (player: Player) -> Unit) {
    Column {
        Text(text = "Select your player:")
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(players) { player ->
                PlayerComposable(
                    player = player,
                    isSelected = player == selectedPlayer,
                    onPlayerSelected = onPlayerSelected
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerComposable(player: Player, isSelected: Boolean, onPlayerSelected: (player: Player) -> Unit) {
    ElevatedCard(
        onClick = { onPlayerSelected(player) },
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = if (isSelected) 8.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier.fillMaxWidth(0.3f),
                imageModel = { player.picture.thumbnail },
                previewPlaceholder = R.drawable.placeholder
            )
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = player.title,
                    fontSize = 20.sp,
                    fontWeight = if (isSelected) FontWeight.W900 else FontWeight.W500
                )
                Text(
                    text = player.name + " " + player.surname,
                    fontSize = 18.sp,
                    fontWeight = if (isSelected) FontWeight.W700 else FontWeight.W300
                )
            }
        }
    }
}

@Composable
@Preview
private fun PlayerPreview() {
    PlayerComposable(
        player = Player(
            "Mr",
            "John",
            "Doe",
            picture = Picture(
                "https://randomuser.me/api/portraits/men/91.jpg",
                "https://randomuser.me/api/portraits/med/men/91.jpg",
                "https://randomuser.me/api/portraits/thumb/men/91.jpg"
            )
        ),
        isSelected = false,
        onPlayerSelected = {}
    )
}

@Composable
@Preview
private fun PlayersListPreview() {
    val selectedPlayer = Player(
        "Mr",
        "Sir",
        "Punce",
        picture = Picture(
            "https://randomuser.me/api/portraits/men/42.jpg",
            "https://randomuser.me/api/portraits/med/men/42.jpg",
            "https://randomuser.me/api/portraits/thumb/men/42.jpg"
        )
    )
    val players = listOf(
        Player(
            "Mr",
            "John",
            "Doe",
            picture = Picture(
                "https://randomuser.me/api/portraits/men/91.jpg",
                "https://randomuser.me/api/portraits/med/men/91.jpg",
                "https://randomuser.me/api/portraits/thumb/men/91.jpg"
            )
        ),
        selectedPlayer,
        Player(
            "Mr",
            "John",
            "Doe",
            picture = Picture(
                "https://randomuser.me/api/portraits/men/91.jpg",
                "https://randomuser.me/api/portraits/med/men/91.jpg",
                "https://randomuser.me/api/portraits/thumb/men/91.jpg"
            )
        )
    )
    PlayersList(players = players, selectedPlayer = selectedPlayer, onPlayerSelected = {})
}
