package edu.ucne.registroapiplanets.domain.usecase

import edu.ucne.registroapiplanets.domain.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetsDetailUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(id: Int) = repository.getPlanetDetail(id)
}