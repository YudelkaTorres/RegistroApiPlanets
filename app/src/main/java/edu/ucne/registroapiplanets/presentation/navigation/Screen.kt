package edu.ucne.registroapiplanets.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object PlanetList : Screen

    @Serializable
    data class PlanetDetail(val id: Int) : Screen
}