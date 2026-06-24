package com.app.duolender_app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNav(val title: String, val icon: ImageVector, val route: String) {
	object Schedule: BottomNav("스케쥴", Icons.Default.DateRange, "schedule")
	object Group: BottomNav("그룹", Icons.Default.Face, "group")
	object Setting: BottomNav("설정", Icons.Default.Person, "setting")

}

@Composable
fun MainAppScreen() {
	val navController = rememberNavController()

	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val parentRoute = navBackStackEntry?.destination?.parent?.route

	val bottomBarRoutes = listOf(
		BottomNav.Schedule.route,
		BottomNav.Group.route,
		BottomNav.Setting.route
	)

	Scaffold(
		bottomBar = {
			if (parentRoute in bottomBarRoutes) {
				BottomNavBar(navController = navController)
			}
		}
	) { paddingValues ->
			NavHost(
				navController = navController,
				startDestination = "auth",
				modifier = Modifier.padding(paddingValues)
			) {
				authGraph(navController)
				scheduleGraph(navController)
				groupGraph(navController)
			}
	}
}

@Composable
fun BottomNavBar(navController: NavHostController) {
	val items = listOf(
		BottomNav.Schedule,
		BottomNav.Group,
		BottomNav.Setting
	)

	NavigationBar(containerColor = Color.White) {
		val navBackStackEntry by navController.currentBackStackEntryAsState()
		val parentRoute = navBackStackEntry?.destination?.parent?.route

		items.forEach { item ->
			NavigationBarItem(
				icon = { Icon(item.icon, contentDescription = item.title) },
				label = { Text(item.title) },
				selected = parentRoute == item.route,
				onClick = {
					navController.navigate(item.route) {
						navController.graph.startDestinationRoute?.let {
							route -> popUpTo(route) {
								saveState = true
							}
							launchSingleTop = true
							restoreState = true
						}
					}
				},
				colors = NavigationBarItemDefaults.colors(
					selectedIconColor = MaterialTheme.colorScheme.primary,
					unselectedIconColor = Color.Gray,
				)
			)
		}
	}

}