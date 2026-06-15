package com.app.duolender_app.ui.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.duolender_app.ui.AppViewModelFactory
import com.app.duolender_app.ui.auth.AuthViewModel
import com.app.duolender_app.ui.schedule.*

fun NavGraphBuilder.scheduleGraph(navController: NavController) {
	navigation(startDestination = "home", route = "schedule") {

		composable("home") {
			MainScreen(
				onNavigateToRegister = {
					navController.navigate("register")
				}
			)
		}

		composable("register") {
			val viewModel: ScheduleViewModel = viewModel(factory = AppViewModelFactory())

			RegisterScreen(
				onBackClick = {
					navController.popBackStack()
				},
				onSaveClick = {
					title, isAllDay, startDate, startTime, endDate, endTime, memo ->
						viewModel.register(title, isAllDay, startDate, startTime, endDate, endTime, memo)
				},
			)
		}

	}
}