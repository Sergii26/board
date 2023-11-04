package com.practice.board

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.practice.board.ui.screens.add_ticket.AddTicketScreen
import com.practice.board.ui.screens.details.DetailScreen
import com.practice.board.ui.screens.home.HomeScreen
import com.practice.board.ui.theme.BoardTheme

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationDestination.Home.name
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationDestination.Home.name) {
            BoardTheme() {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(navController = navController)
                }
            }
        }

        composable(NavigationDestination.AddTicket.name) {
            BoardTheme() {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AddTicketScreen(navController = navController)
                }
            }
        }

        composable(
            "${NavigationDestination.Detail.name}/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { navEntry ->
            BoardTheme() {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DetailScreen(navController = navController, navEntry.arguments?.getInt("taskId") ?: -1)
                }
            }
        }
    }
}

enum class NavigationDestination {
    Home,
    AddTicket,
    Detail,
}
