package pini.mattia.coachtimer.ui.sessionscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pini.mattia.coachtimer.R
import pini.mattia.coachtimer.ui.customcomposables.Graph
import pini.mattia.coachtimer.ui.customcomposables.MultiFabItem
import pini.mattia.coachtimer.ui.customcomposables.MultiFabState
import pini.mattia.coachtimer.ui.customcomposables.MultiFloatingActionButton
import pini.mattia.coachtimer.ui.mainscreen.MainScreenViewModel
import pini.mattia.coachtimer.ui.theme.CoachTimerTheme
import pini.mattia.coachtimer.ui.theme.stopWatchDisplay
import pini.mattia.coachtimer.ui.theme.stopwatchFontRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen(navController: NavController) {
    val viewModel: SessionScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    val fabState = remember {
        mutableStateOf(MultiFabState.COLLAPSED)
    }
    CoachTimerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.LightGray),
                    title = {
                        Text(text = "Training session")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.saveSession()
                            navController.popBackStack()
                        }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Back")
                        }
                    }
                )
            },
            floatingActionButton = {
                MultiFloatingActionButton(
                    fabIcon = Icons.Filled.ArrowDropDown,
                    items = if (viewState.trainingSession != null) listOf(

                        MultiFabItem(
                            "Stop",
                            {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_stop_24),
                                    contentDescription = "Lap icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            viewState.trainingSession!!.isSessionStarted,
                            onFabClick = viewModel::stopSession
                        ),
                        MultiFabItem(
                            "Lap",
                            {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_lap_time_24),
                                    contentDescription = "Lap icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            viewState.trainingSession!!.isSessionStarted,
                            onFabClick = viewModel::addLap
                        ),
                        MultiFabItem(
                            "Start!",
                            {
                                Icon(
                                    imageVector = Icons.Filled.PlayArrow,
                                    contentDescription = "Start icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            !viewState.trainingSession!!.isSessionStarted,
                            onFabClick = viewModel::startSession
                        )
                    ) else emptyList(),
                    toState = fabState.value,
                    stateChanged = { newFabState -> fabState.value = newFabState }
                )
            }
        ) {
            Column(
                Modifier
                    .padding(it)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (viewState.status) {
                    MainScreenViewModel.Status.LOADING -> {}
                    MainScreenViewModel.Status.FAILED -> {}
                    MainScreenViewModel.Status.SUCCESS -> {
                        val trainingSessionUI = viewState.trainingSession!!
                        BackHandler(true) {
                            viewModel.saveSession()
                            navController.popBackStack()
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
                        ) {
                            PlayerComposable(
                                imageUrl = trainingSessionUI.player.picture.medium,
                                title = trainingSessionUI.player.title,
                                name = trainingSessionUI.player.name,
                                surname = trainingSessionUI.player.surname
                            )
                            WatchComposable(
                                currentTime = trainingSessionUI.formattedElapsedTime
                            )
                            PerformancesComposable(performances = trainingSessionUI.performancesUI)

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Lap times graph(millis)",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Graph(
                                modifier = Modifier,
                                values = trainingSessionUI.performancesUI.laps,
                                trainingSessionUI.performancesUI.averageLapTime
                            )
                            // add some bottom space for readability
                            Spacer(modifier = Modifier.height(64.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerComposable(imageUrl: String, title: String, name: String, surname: String) {
    Surface(shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.clip(RoundedCornerShape(36.dp))) {
                GlideImage(
                    modifier = Modifier
                        .width(72.dp)
                        .height(72.dp),
                    imageModel = { imageUrl },
                    previewPlaceholder = R.drawable.placeholder,
                    imageOptions = ImageOptions(contentScale = ContentScale.Fit)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            )
            Text(
                text = "$name $surname",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        }
    }
}

@Composable
@Preview
fun PlayerComposablePreview() {
    PlayerComposable(imageUrl = "", title = "Mr", name = "Gauf", surname = "Fres")
}

@Composable
fun WatchComposable(
    currentTime: String
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Timer", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(stopWatchDisplay)
                    .padding(all = 24.dp)
                    .fillMaxWidth(0.75f)
            ) {
                Text(
                    text = currentTime,
                    fontSize = 32.sp,
                    fontFamily = stopwatchFontRegular,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview
@Composable
fun WatchComposablePreview() {
    WatchComposable(currentTime = "35' 40\".987")
}

@Composable
fun PerformancesComposable(performances: PerformancesUI) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Performances", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Lap", fontWeight = FontWeight.SemiBold)
                    Text(text = performances.numberOfLaps)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Avg. Time", fontWeight = FontWeight.SemiBold)
                    Text(text = performances.averageLapTimeFormatted)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Max Speed", fontWeight = FontWeight.SemiBold)
                    Text(text = performances.maxSpeed)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Avg. Speed", fontWeight = FontWeight.SemiBold)
                    Text(text = performances.averageSpeed)
                }
            }
        }
    }
}

@Composable
@Preview
private fun PerformancesPreview() {
    PerformancesComposable(
        performances = PerformancesUI(
            "1",
            "7.8 m/s",
            listOf("00\"32'.540"),
            listOf(34723L),
            "7.8 m/s",
            "00\"32'.540",
            7000
        )
    )
}
