package edu.ucne.registroapiplanets.presentation.detail.detailPlanet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import edu.ucne.registroapiplanets.domain.model.Planet
import edu.ucne.registroapiplanets.presentation.detail.detailPlanet.PlanetDetailUiState
import edu.ucne.registroapiplanets.presentation.detail.detailPlanet.PlanetDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailScreen(
    viewModel: PlanetDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PlanetDetailBodyScreen(
        state = state,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailBodyScreen(
    state: PlanetDetailUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.planet?.name ?: "Detalle del Planeta") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error?.let { errorMessage ->
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }

        state.planet?.let { planet ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = planet.image,
                    contentDescription = planet.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(planet.name, style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = "Estado: ${if (planet.isDestroyed) "Destruido" else "Intacto"}",
                    color = if (planet.isDestroyed) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(planet.description, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanetDetailBodyScreenPreview() {
    val samplePlanet = Planet(
        id = 1,
        name = "Namek",
        isDestroyed = false,
        description = "Un planeta pacífico con tres soles, hogar de los Namekianos y lugar de origen de las esferas del dragón originales.",
        image = ""
    )

    val fakeState = PlanetDetailUiState(
        isLoading = false,
        planet = samplePlanet,
        error = null
    )

    MaterialTheme {
        Surface {
            PlanetDetailBodyScreen(
                state = fakeState,
                onBack = {}
            )
        }
    }
}