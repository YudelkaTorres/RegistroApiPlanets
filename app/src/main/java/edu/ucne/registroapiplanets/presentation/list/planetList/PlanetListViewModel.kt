package edu.ucne.registroapiplanets.presentation.list.planetList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroapiplanets.data.remote.Resource
import edu.ucne.registroapiplanets.domain.usecase.GetPlanetsUseCase
import edu.ucne.registroapiplanets.presentation.list.planetList.PlanetListEvent
import edu.ucne.registroapiplanets.presentation.list.planetList.PlanetListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetListViewModel @Inject constructor(
    private val getPlanetsUseCase: GetPlanetsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PlanetListUiState())
    val state = _state.asStateFlow()

    init {
        loadPlanets()
    }

    fun onEvent(event: PlanetListEvent) {
        when (event) {
            is PlanetListEvent.UpdateFilters -> _state.update {
                it.copy(
                    filterName = event.name,
                    filterStatus = event.status
                )
            }
            PlanetListEvent.Search -> loadPlanets()
        }
    }

    private fun loadPlanets() {
        viewModelScope.launch {
            val current = _state.value

            getPlanetsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }

                    is Resource.Success -> {
                        val allPlanets = result.data ?: emptyList()

                        val filteredPlanets = allPlanets.filter { planeta ->
                            val matchesName = current.filterName.isBlank() ||
                                    planeta.name.contains(current.filterName, ignoreCase = true)

                            val statusText = if (planeta.isDestroyed) "destruido" else "intacto"
                            val matchesStatus = current.filterStatus.isBlank() ||
                                    statusText.contains(current.filterStatus, ignoreCase = true)

                            matchesName && matchesStatus
                        }

                        _state.update {
                            it.copy(
                                isLoading = false,
                                planets = filteredPlanets
                            )
                        }
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
}