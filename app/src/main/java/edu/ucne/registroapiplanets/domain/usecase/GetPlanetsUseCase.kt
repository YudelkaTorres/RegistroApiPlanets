package edu.ucne.registroapiplanets.domain.usecase

import edu.ucne.registroapiplanets.domain.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetsUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    operator fun invoke(
        page: Int = 1,
        limit: Int = 10,
        name: String? = null
    ) = repository.getPlanets(page, limit, name)
}