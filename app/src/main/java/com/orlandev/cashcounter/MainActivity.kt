package com.orlandev.cashcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orlandev.cashcounter.ui.screens.AddHistoryScreen
import com.orlandev.cashcounter.ui.screens.HistoryScreen
import com.orlandev.cashcounter.ui.screens.HomeScreen
import com.orlandev.cashcounter.ui.screens.HomeViewModel
import com.orlandev.cashcounter.ui.theme.CashCounterTheme
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel = koinViewModel<HomeViewModel>()
            CashCounterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = CashNavGraph.HomeScreenRoute.route
                    ) {

                        composable(route = CashNavGraph.HomeScreenRoute.route) {
                            HomeScreen(homeViewModel = viewModel, onNavToAddHistory = {

                                navController.navigate(CashNavGraph.AddHistoryScreenRoute.route)

                            }) {
                                navController.navigate(CashNavGraph.HistoryScreenRoute.route)
                            }
                        }

                        composable(route = CashNavGraph.AddHistoryScreenRoute.route) {
                            AddHistoryScreen(
                                modifier = Modifier.fillMaxSize(),
                                navController = navController,
                                viewModel = viewModel
                            )
                        }

                        composable(route = CashNavGraph.HistoryScreenRoute.route) {
                            HistoryScreen() {
                                navController.navigate(CashNavGraph.HomeScreenRoute.route)
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed class CashNavGraph(val route: String) {
    data object HomeScreenRoute : CashNavGraph("home_screen")
    data object HistoryScreenRoute : CashNavGraph("history_screen")
    data object AddHistoryScreenRoute : CashNavGraph("add_history_screen")
}