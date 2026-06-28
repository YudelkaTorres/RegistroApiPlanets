package edu.ucne.registroapiplanets.presentation.detail.detailPlanet

import edu.ucne.registroapiplanets.domain.model.Planet

data class PlanetDetailUiState (
    val isLoading: Boolean = false,
    val planet: Planet? = null,
    val error: String? = null
)