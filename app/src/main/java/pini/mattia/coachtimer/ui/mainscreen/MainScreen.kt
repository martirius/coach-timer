@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package pini.mattia.coachtimer.ui.mainscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import pini.mattia.coachtimer.R
import pini.mattia.coachtimer.model.player.Picture
import pini.mattia.coachtimer.model.player.Player
import pini.mattia.coachtimer.ui.theme.CoachTimerTheme

@Composable
fun MainScreen(navController: NavController) {
    val launchedEffectKey = remember {
        "key"
    }
    val viewModel: MainScreenViewModel = hiltViewModel()
    // waiting for collectAsStateWithLifecycle to comes out of alpha
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = launchedEffectKey) {
        viewModel.navigation.collect {
            navController.navigate(it)
        }
    }

    CoachTimerTheme {
        Scaffold(
            topBar = {
                Box(modifier = Modifier.padding(all = 24.dp)) {
                    Text(text = "Players", fontSize = 32.sp)
                }
            },
            floatingActionButton = {
                if (viewState.selectedPlayer != null) {
                    FloatingActionButton(onClick = { viewModel.openInputDialog() }) {
                        Icon(Icons.Filled.Create, "Create icon")
                    }
                }
            }
        ) { paddingValues ->
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
            if (viewState.inputDialogState.showInputDialog && viewState.selectedPlayer != null) {
                InputDialog(
                    selectedPlayer = viewState.selectedPlayer!!,
                    onStartPressed = viewModel::startSession,
                    onCloseDialog = viewModel::closeInputDialogPressed,
                    lapDistanceValue = viewState.inputDialogState.sessionLapDistance,
                    isDatavalid = viewState.inputDialogState.dataValid,
                    onLapDistanceChanged = viewModel::lapDistanceChanged
                )
            }
        }
    }
}

@Composable
fun PlayersList(players: List<Player>, selectedPlayer: Player?, onPlayerSelected: (player: Player) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text(text = stringResource(R.string.select_player))
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
                modifier = Modifier.width(56.dp).height(56.dp),
                imageModel = { player.picture.thumbnail },
                previewPlaceholder = R.drawable.placeholder
            )
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = player.title,
                    fontSize = 18.sp,
                    fontWeight = if (isSelected) FontWeight.W900 else FontWeight.W500
                )
                Text(
                    text = player.name + " " + player.surname,
                    fontSize = 16.sp,
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

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun InputDialog(
    selectedPlayer: Player,
    onStartPressed: () -> Unit,
    lapDistanceValue: String,
    onLapDistanceChanged: (String) -> Unit,
    isDatavalid: Boolean,
    onCloseDialog: () -> Unit
) {
    // unfortunately BottomSheetDialog is still unavailable in Material 3 compose library
    Dialog(
        onDismissRequest = onCloseDialog,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        AnimatedVisibility(
            true,
            enter = slideInVertically(
                initialOffsetY = { it }
            ),
            exit = slideOutVertically(
                targetOffsetY = { it }
            )
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column {
                    // dialog title
                    Row(
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onCloseDialog) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(R.string.icon_close_description)
                            )
                        }
                        Text(text = stringResource(R.string.input_dialog_title), fontSize = 32.sp)
                    }
                    Column(modifier = Modifier.padding(all = 24.dp)) {
                        Text(text = stringResource(R.string.selected_player))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GlideImage(
                                modifier = Modifier.width(56.dp).height(56.dp),
                                imageModel = { selectedPlayer.picture.thumbnail },
                                previewPlaceholder = R.drawable.placeholder
                            )
                            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                                Text(
                                    text = selectedPlayer.title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W500
                                )
                                Text(
                                    text = selectedPlayer.name + " " + selectedPlayer.surname,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W300
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = stringResource(R.string.input_lap_distance))
                        TextField(
                            value = lapDistanceValue,
                            onValueChange = onLapDistanceChanged,
                            isError = !isDatavalid,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(text = "5 - 100")
                            },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Box(modifier = Modifier.fillMaxSize()) {
                            ElevatedButton(
                                onClick = onStartPressed,
                                enabled = isDatavalid,
                                modifier = Modifier.padding(vertical = 16.dp).align(
                                    Alignment.BottomEnd
                                )
                            ) {
                                Text(text = stringResource(R.string.input_start_session), fontSize = 24.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun InputDialogPreview() {
    InputDialog(
        selectedPlayer = Player(
            "Mr",
            "Sir",
            "Punce",
            picture = Picture(
                "https://randomuser.me/api/portraits/men/42.jpg",
                "https://randomuser.me/api/portraits/med/men/42.jpg",
                "https://randomuser.me/api/portraits/thumb/men/42.jpg"
            )
        ),
        onStartPressed = {},
        lapDistanceValue = "5",
        isDatavalid = true,
        onLapDistanceChanged = {}
    ) {
    }
}
