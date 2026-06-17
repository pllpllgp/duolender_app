package com.app.duolender_app.ui.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.app.duolender_app.ui.AppViewModelFactory
import com.app.duolender_app.ui.schedule.MainScreen
import com.app.duolender_app.ui.schedule.RegisterScreen
import com.app.duolender_app.ui.schedule.ScheduleViewModel
import java.time.LocalDate

fun NavGraphBuilder.scheduleGraph(navController: NavController) {
	navigation(startDestination = "home", route = "schedule") {

		composable("home") {
			MainScreen(
				onNavigateToRegister = { date ->
					navController.navigate("register/${date}")
				}
			)
		}

		composable(
			route = "register/{date}",
			arguments = listOf(navArgument("date") { type = NavType.StringType })
		) { backStackEntry ->
			val context = LocalContext.current
			val viewModel: ScheduleViewModel = viewModel(factory = AppViewModelFactory(context))
			val dateStr = backStackEntry.arguments?.getString("date") ?: LocalDate.now().toString()

			RegisterScreen(
				initialDate = dateStr,
				onBackClick = {
					navController.popBackStack()
				},
				onSaveClick = { title, startDate, endDate, memo ->
					viewModel.register(title, startDate, endDate, memo)
				},
				onRegisterSuccess = {
					navController.navigate("home")
				}
			)
		}

	}
}