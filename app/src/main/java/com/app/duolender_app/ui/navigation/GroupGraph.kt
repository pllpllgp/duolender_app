package com.app.duolender_app.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.duolender_app.ui.group.CreateScreen
import com.app.duolender_app.ui.group.GroupScreen

fun NavGraphBuilder.groupGraph(navController: NavController) {
	navigation(startDestination = "main", route = "group") {

		composable("main") {
			GroupScreen(
				onNavigateToCreate = {
					navController.navigate("create")
				}
			)
		}

		composable("create") {
			CreateScreen(
				onBack ={
					navController.popBackStack()
				}
			)
		}

	}

}