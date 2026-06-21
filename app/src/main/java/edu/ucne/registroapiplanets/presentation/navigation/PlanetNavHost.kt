package edu.ucne.registroapiplanets.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.registroapiplanets.presentation.detail.PlanetDetailScreen
import edu.ucne.registroapiplanets.presentation.list.PlanetListScreen

@Composable
fun PlanetNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PlanetList
    ) {
        composable<Screen.PlanetList> {
            PlanetListScreen(
                onPlanetClick = { planetId ->
                    navController.navigate(Screen.PlanetDetail(id = planetId))
                }
            )
        }
        composable<Screen.PlanetDetail> {
            PlanetDetailScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}