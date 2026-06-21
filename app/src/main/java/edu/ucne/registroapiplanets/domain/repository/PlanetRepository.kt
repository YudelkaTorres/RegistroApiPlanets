package edu.ucne.registroapiplanets.domain.repository

import edu.ucne.registroapiplanets.data.remote.Resource
import edu.ucne.registroapiplanets.domain.model.Planet
import kotlinx.coroutines.flow.Flow

interface PlanetRepository {
    fun getPlanets(
        page: Int = 1,
        limit: Int = 10,
        name: String? = null
    ): Flow<Resource<List<Planet>>>

    fun getPlanetDetail(id: Int): Flow<Resource<Planet>>
}