package edu.ucne.registroapiplanets.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.registroapiplanets.presentation.detail.detailPlanet.PlanetDetailScreen
import edu.ucne.registroapiplanets.presentation.list.planetList.PlanetListScreen
import edu.ucne.registroapiplanets.presentation.list.characterList.CharacterListScreen
import edu.ucne.registroapiplanets.presentation.detail.detailCharacter.CharacterDetailScreen
import edu.ucne.registroapiplanets.presentation.detail.detailCharacter.CharacterDetailViewModel

@Composable
fun PlanetNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CharacterList
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

        composable<Screen.CharacterList> {
            CharacterListScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(Screen.CharacterDetail(id = characterId))
                }
            )
        }

        composable<Screen.CharacterDetail> {
            val viewModel: CharacterDetailViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            CharacterDetailScreen(
                state = state,
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}