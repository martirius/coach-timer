package pini.mattia.coachtimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import pini.mattia.coachtimer.Destinations.LEADERBOARD_SCREEN
import pini.mattia.coachtimer.Destinations.MAIN_SCREEN
import pini.mattia.coachtimer.Destinations.SESSION_SCREEN
import pini.mattia.coachtimer.ui.mainscreen.MainScreen
import pini.mattia.coachtimer.ui.sessionscreen.SessionScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "session") {
                navigation(startDestination = MAIN_SCREEN, route = "session") {
                    composable(MAIN_SCREEN) {
                        MainScreen(navController)
                    }
                    composable(
                        SESSION_SCREEN,
                        arguments = listOf(
                            navArgument("playerId") {
                                type = NavType.StringType
                            },
                            navArgument("lapDistance") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        SessionScreen(navController = navController)
                    }
                }
                composable(LEADERBOARD_SCREEN) {
                }
            }
        }
    }
}
