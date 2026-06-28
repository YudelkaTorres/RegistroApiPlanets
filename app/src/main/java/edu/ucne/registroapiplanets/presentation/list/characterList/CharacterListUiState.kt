package edu.ucne.registroapiplanets.presentation.list.characterList

import edu.ucne.registroapiplanets.domain.model.Character

data class CharacterListUiState (
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val error: String? = null,
    val filterName: String = "",
    val filterGender: String = "",
    val filterRace: String = ""
)