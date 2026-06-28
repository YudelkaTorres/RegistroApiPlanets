package edu.ucne.registroapiplanets.presentation.detail.detailCharacter

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.ucne.registroapiplanets.domain.model.Character
import edu.ucne.registroapiplanets.ui.theme.RegistroApiPlanetsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    state: CharacterDetailUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Personaje") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        state.character?.let { character ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {

                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )

                Text(character.name)
                Text("Raza: ${character.race}")
                Text("Género: ${character.gender}")
                Text("Ki: ${character.ki}")
                Text("Máx Ki: ${character.maxKi}")
                Text(character.description)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    val sampleCharacter = Character(
        id = 3,
        name = "Gohan",
        ki = "32.000.000",
        race = "Half-Saiyan",
        gender = "Male",
        description = "Hijo mayor de Goku, posee un gran potencial oculto que despierta en los momentos críticos.",
        image = "",
        maxKi = "120.000.000.000"
    )

    val state = CharacterDetailUiState(
        isLoading = false,
        character = sampleCharacter,
        error = null
    )

    RegistroApiPlanetsTheme {
        Surface {
            CharacterDetailScreen(
                state = state,
                onBack = {}
            )
        }
    }
}