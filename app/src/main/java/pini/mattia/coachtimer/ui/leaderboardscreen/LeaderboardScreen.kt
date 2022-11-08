package pini.mattia.coachtimer.ui.leaderboardscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import pini.mattia.coachtimer.R
import pini.mattia.coachtimer.model.trainingsession.TrainingSessionFilter
import pini.mattia.coachtimer.ui.mainscreen.MainScreenViewModel
import pini.mattia.coachtimer.ui.theme.CoachTimerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderBoardScreen() {
    val viewModel: LeaderBoardScreenViewModel = hiltViewModel()

    val viewState by viewModel.viewState.collectAsState()

    CoachTimerTheme {
        Scaffold(
            topBar = {
                Box(modifier = Modifier.padding(all = 24.dp)) {
                    Text(text = "Leaderboard", fontSize = 32.sp)
                }
            }

        ) { paddingValues ->
            Box(Modifier.padding(paddingValues)) {
                when (viewState.status) {
                    MainScreenViewModel.Status.LOADING -> {}
                    MainScreenViewModel.Status.FAILED -> {}
                    MainScreenViewModel.Status.SUCCESS -> {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Sort player by:")
                            Row {
                                ElevatedFilterChip(
                                    selected = viewState.selectedFilter == TrainingSessionFilter.PEAK_SPEED,
                                    onClick = { viewModel.setFilter(TrainingSessionFilter.PEAK_SPEED) },
                                    label = {
                                        Text(text = "Peak speed")
                                    }
                                )
                                ElevatedFilterChip(
                                    selected = viewState.selectedFilter == TrainingSessionFilter.ENDURANCE,
                                    onClick = { viewModel.setFilter(TrainingSessionFilter.ENDURANCE) },
                                    label = {
                                        Text(text = "Endurance")
                                    }
                                )
                            }
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 24.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(viewState.trainingSessions) { trainingSession ->
                                    LeaderboardItem(
                                        trainingSessionUI = trainingSession,
                                        sessionFilter = viewState.selectedFilter
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardItem(trainingSessionUI: TrainingSessionUI, sessionFilter: TrainingSessionFilter) {
    ElevatedCard(
        onClick = { },
        colors = CardDefaults.cardColors()
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier.width(56.dp).height(56.dp),
                    imageModel = { trainingSessionUI.player.picture.medium },
                    previewPlaceholder = R.drawable.placeholder
                )
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text(
                        text = trainingSessionUI.player.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500
                    )
                    Text(
                        text = trainingSessionUI.player.name + " " + trainingSessionUI.player.surname,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W300
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Performances", fontSize = 20.sp, fontWeight = FontWeight.Medium)
            Row(modifier = Modifier.fillMaxWidth()) {
                // max speed
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(text = "Peak speed", fontWeight = FontWeight.SemiBold)
                    Text(text = trainingSessionUI.peakSpeed, fontWeight = if (sessionFilter == TrainingSessionFilter.PEAK_SPEED) FontWeight.Bold else FontWeight.Normal)
                }
                // max
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Endurance (laps)", fontWeight = FontWeight.SemiBold)
                    Text(text = trainingSessionUI.endurance, fontWeight = if (sessionFilter == TrainingSessionFilter.ENDURANCE) FontWeight.Bold else FontWeight.Normal)
                }
            }
        }
    }
}
