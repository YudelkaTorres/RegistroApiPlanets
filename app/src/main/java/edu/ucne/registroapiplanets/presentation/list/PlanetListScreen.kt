package edu.ucne.registroapiplanets.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetListScreen(
    viewModel: PlanetListViewModel = hiltViewModel(),
    onPlanetClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PlanetListBodyScreen(
        state = state,
        onEvent = viewModel:: onEvent,
        onPlanetClick = onPlanetClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetListBodyScreen(
    state: PlanetListUiState,
    onEvent: (PlanetListEvent) -> Unit,
    onPlanetClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Planetas Dragon Ball") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            FilterSection(
                name = state.filterName,
                status = state.filterStatus,
                onEvent = onEvent
            )

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.planets) { planet ->
                    PlanetListItem(
                        planet = planet,
                        onClick = { onPlanetClick(planet.id) }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
fun FilterSection(
    name: String,
    status: String,
    onEvent: (PlanetListEvent) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { onEvent(PlanetListEvent.UpdateFilters(it, status)) },
                label = { Text("Nombre (ej. Namek)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = status,
                onValueChange = { onEvent(PlanetListEvent.UpdateFilters(name, it)) },
                label = { Text("Estado (ej. Intacto / Destruido)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { onEvent(PlanetListEvent.Search) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Buscar")
            }
        }
    }
}

@Composable
fun PlanetListItem(
    planet: Planet,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = planet.image,
                contentDescription = planet.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(planet.name)
                Text(if (planet.isDestroyed) "Estado: Destruido" else "Estado: Intacto")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanetListBodyScreenPreview() {
    val samplePlanets = listOf(
        Planet(
            id = 1,
            name = "Namek",
            isDestroyed = true,
            description = "El planeta natal de los Namekianos.",
            image = ""
        ),
        Planet(
            id = 2,
            name = "Tierra",
            isDestroyed = false,
            description = "El planeta hogar de los humanos y Saiyans de la Tierra.",
            image = ""
        )
    )

    val state = PlanetListUiState(
        planets = samplePlanets,
        filterName = "Namek"
    )

    MaterialTheme {
        Surface {
            PlanetListBodyScreen(
                state = state,
                onEvent = {},
                onPlanetClick = {}
            )
        }
    }
}
