package com.app.duolender_app.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.duolender_app.data.auth.network.AuthApiService
import com.app.duolender_app.ui.AppViewModelFactory
import com.app.duolender_app.ui.auth.AuthViewModel
import com.app.duolender_app.ui.auth.LoginScreen
import com.app.duolender_app.ui.auth.SignupScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
	navigation(startDestination = "login", route = "auth") {

		composable("login") {
			LoginScreen(
				onLoginSuccess = {
					navController.navigate("schedule") {
						popUpTo("auth") {
							inclusive = true
						}
					}
				},
				onNavigateToSignup = {
					navController.navigate("signup")
				}
			)
		}

		composable("signup") {
			val context = LocalContext.current
			val viewModel: AuthViewModel = viewModel(factory = AppViewModelFactory(context))
			val signupResult by viewModel.signupResult.collectAsState()

			SignupScreen(
				signupResult = signupResult,
				onSignupClick = { userId, userPw, userNm, userEmail, userPhone ->
					viewModel.signup(userId, userPw, userNm, userEmail, userPhone)
				},
				onBackToLogin = {
					navController.popBackStack()
				}
			)
		}
	}
}