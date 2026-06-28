package edu.ucne.registroapiplanets.presentation.list.characterList

sealed interface CharacterListEvent {
    data class UpdateFilters(
        val name: String,
        val gender: String,
        val race: String
    ) : CharacterListEvent

    data object Search : CharacterListEvent
}