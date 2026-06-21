package edu.ucne.registroapiplanets.presentation.list

import edu.ucne.registroapiplanets.domain.model.Planet

data class PlanetListUiState(
    val isLoading: Boolean = false,
    val planets: List<Planet> = emptyList(),
    val error: String? = null,
    val filterName: String = "",
    val filterStatus: String = ""
)
