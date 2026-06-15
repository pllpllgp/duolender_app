package com.app.duolender_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier

@Composable
fun MainAppScreen() {
	val navController = rememberNavController()

	NavHost(
		navController = navController,
		startDestination = "auth",
		modifier = Modifier
	) {
		authGraph(navController)
		scheduleGraph(navController)

	}
}