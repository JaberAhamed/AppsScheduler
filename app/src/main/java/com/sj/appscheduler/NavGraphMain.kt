package com.sj.appscheduler

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sj.appscheduler.ui.screens.home.HomeScreen
import com.sj.appscheduler.ui.screens.schedule.ScheduleScreen

sealed class Screen(
    val route: String
) {
    data object Home : Screen("home")
}

sealed class HomeScreen(
    val route: String
) {
    data object MainScreen : HomeScreen("home/index")
    data object ScheduleScreen : HomeScreen("schedule")
}

private fun NavGraphBuilder.addHomeScreen(navController: NavHostController) {
    navigation(
        route = Screen.Home.route,
        startDestination = HomeScreen.MainScreen.route
    ) {
        composable(HomeScreen.MainScreen.route) {
            HomeScreen(
                navigateToScheduleScreen = {
                    navController.navigate(HomeScreen.ScheduleScreen.route)
                }
            )
        }

        composable(HomeScreen.ScheduleScreen.route) {
            ScheduleScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        addHomeScreen(navController)
    }
}
