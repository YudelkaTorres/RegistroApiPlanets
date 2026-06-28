package edu.ucne.registroapiplanets.presentation.detail.detailCharacter

import edu.ucne.registroapiplanets.domain.model.Character

data class CharacterDetailUiState (
    val isLoading: Boolean = false,
    val character: Character? = null,
    val error: String? = null
)