package com.sj.appscheduler

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    MainScreenSkeleton()
}

@Composable
fun MainScreenSkeleton(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    MainNavHost(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navController = navController
    )
}
