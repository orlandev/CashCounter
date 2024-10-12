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
import com.orlandev.cashcounter.ui.screens.HistoryScreen
import com.orlandev.cashcounter.ui.screens.HomeScreen
import com.orlandev.cashcounter.ui.theme.CashCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
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
                            HomeScreen() {
                                navController.navigate(CashNavGraph.HistoryScreenRoute.route)
                            }
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
}