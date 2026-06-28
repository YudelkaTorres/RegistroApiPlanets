package edu.ucne.registroapiplanets.presentation.list.characterList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroapiplanets.data.remote.Resource
import edu.ucne.registroapiplanets.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterListUiState())
    val state = _state.asStateFlow()

    init {
        loadCharacters()
    }

    fun onEvent(event: CharacterListEvent) {
        when (event) {
            is CharacterListEvent.UpdateFilters -> _state.update {
                it.copy(
                    filterName = event.name,
                    filterGender = event.gender,
                    filterRace = event.race
                )
            }
            CharacterListEvent.Search -> loadCharacters()
        }
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            val current = _state.value

            getCharactersUseCase(
                name = null,
                gender = null,
                race = null
            ).collect { result ->

                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }

                    is Resource.Success -> {
                        val allCharacters = result.data ?: emptyList()

                        if (current.filterName.isBlank() && current.filterGender.isBlank() && current.filterRace.isBlank()) {

                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    characters = allCharacters
                                )
                            }

                        } else {
                            val filteredCharacters = allCharacters.filter { character ->
                                val matchesName = current.filterName.isBlank() ||
                                        character.name.contains(current.filterName, ignoreCase = true)

                                val matchesGender = current.filterGender.isBlank() ||
                                        character.gender.contains(current.filterGender, ignoreCase = true)

                                val matchesRace = current.filterRace.isBlank() ||
                                        character.race.contains(current.filterRace, ignoreCase = true)

                                matchesName && matchesGender && matchesRace
                            }

                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    characters = filteredCharacters
                                )
                            }
                        }
                    }

                    is Resource.Error ->
                        _state.update {
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