package edu.ucne.registroapiplanets.presentation.list.characterList

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
import edu.ucne.registroapiplanets.domain.model.Character
import edu.ucne.registroapiplanets.ui.theme.RegistroApiPlanetsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
    onCharacterClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CharacterListBodyScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onCharacterClick = onCharacterClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListBodyScreen(
    state: CharacterListUiState,
    onEvent: (CharacterListEvent) -> Unit,
    onCharacterClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Personajes Dragon Ball") }
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
                gender = state.filterGender,
                race = state.filterRace,
                onEvent = onEvent,
            )

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.characters) { character ->
                    CharacterItem(
                        character = character,
                        onClick = { onCharacterClick(character.id) }
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
    gender: String,
    race: String,
    onEvent: (CharacterListEvent) -> Unit,
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
                onValueChange = { onEvent(CharacterListEvent.UpdateFilters(it, gender, race)) },
                label = { Text("Nombre (ej. Gohan)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = gender,
                    onValueChange = { onEvent(CharacterListEvent.UpdateFilters(name, it, race)) },
                    label = { Text("Género") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = race,
                    onValueChange = { onEvent(CharacterListEvent.UpdateFilters(name, gender, it)) },
                    label = { Text("Raza") },
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = { onEvent(CharacterListEvent.Search) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Buscar")
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(character.name)
                Text("${character.race} • ${character.gender}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListBodyScreenPreview() {
    val sampleCharacters = listOf(
        Character(
            id = 3,
            name = "Gohan",
            ki = "32.000.000",
            race = "Half-Saiyan",
            gender = "Male",
            description = "Hijo mayor de Goku, posee un gran potencial oculto que despierta en los momentos críticos.",
            image = "",
            maxKi = "120.000.000.000"
        ),
        Character(
            id = 4,
            name = "Piccolo",
            ki = "28.000.000",
            race = "Namekian",
            gender = "Male",
            description = "Guerrero namekiano y sabio estratega que se convirtió en el mentor de Gohan.",
            image = "",
            maxKi = "55.000.000.000"
        )
    )
    val state = CharacterListUiState(
        characters = sampleCharacters,
        filterName = "Gohan"
    )

    RegistroApiPlanetsTheme {
        Surface {
            CharacterListBodyScreen(
                state = state,
                onEvent = {},
                onCharacterClick = {}
            )
        }
    }
}