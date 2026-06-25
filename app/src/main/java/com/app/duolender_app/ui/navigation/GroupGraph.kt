package com.app.duolender_app.ui.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.duolender_app.ui.AppViewModelFactory
import com.app.duolender_app.ui.group.GroupRegisterScreen
import com.app.duolender_app.ui.group.GroupMainScreen
import com.app.duolender_app.ui.group.GroupViewModel

fun NavGraphBuilder.groupGraph(navController: NavController) {
	navigation(startDestination = "groupMain", route = "group") {

		composable("groupMain") {
			GroupMainScreen(
				onNavigateToCreate = {
					navController.navigate("groupRegister")
				}
			)
		}

		composable("groupRegister") {
			val context = LocalContext.current
			val viewModel: GroupViewModel = viewModel(factory = AppViewModelFactory(context))

			GroupRegisterScreen(
				onBack = {
					navController.popBackStack()
				},
				onSaveClick = {groupNm, groupMemo ->
					viewModel.groupRegister(groupNm, groupMemo)
				}
			)
		}

	}

}