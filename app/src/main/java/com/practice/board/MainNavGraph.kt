package com.practice.board

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
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
    startDestination: String = NavDest.Home.getComposeRoute()
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavDest.Home.getComposeRoute()) {
            BoardTheme() {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(navController = navController)
                }
            }
        }

        composable(NavDest.AddTicket.getComposeRoute()) {
            BoardTheme() {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AddTicketScreen(navController = navController)
                }
            }
        }

        composable(
            NavDest.Detail.getComposeRoute(),
            arguments = listOf(navArgument(NavDest.Detail.TASK_ID_KEY) { type = NavType.IntType })
        ) { navEntry ->
            BoardTheme() {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DetailScreen(
                        navController = navController,
                        navEntry.getInt(NavDest.Detail.TASK_ID_KEY)
                    )
                }
            }
        }
    }
}

private fun NavBackStackEntry.getInt(key: String): Int {
    return arguments?.getInt(key) ?: -1
}

sealed class NavDest(protected val route: String) {
    object Home : NavDest("home") {
        fun getNavRoute(): String {
            return route
        }
    }
    object AddTicket : NavDest("addTicket") {
        fun getNavRoute(): String {
            return route
        }
    }
    object Detail : NavDest("details") {
        const val TASK_ID_KEY = "taskId"

        override fun getComposeRoute(): String {
            return "$route/{$TASK_ID_KEY}"
        }

        fun getNavRoute(taskId: Int): String {
            return "$route/$taskId"
        }
    }

    open fun getComposeRoute(): String {
        return route
    }
}