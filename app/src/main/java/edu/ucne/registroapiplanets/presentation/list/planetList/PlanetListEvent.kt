package edu.ucne.registroapiplanets.presentation.list.planetList

sealed interface PlanetListEvent {

    data class UpdateFilters(
        val name: String,
        val status: String
    ) : PlanetListEvent

    data object Search: PlanetListEvent
}