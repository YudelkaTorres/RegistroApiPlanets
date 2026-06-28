package edu.ucne.registroapiplanets.domain.repository

import edu.ucne.registroapiplanets.data.remote.Resource
import kotlinx.coroutines.flow.Flow
import edu.ucne.registroapiplanets.domain.model.Character
interface CharacterRepository {
    fun getCharacters(
        page: Int,
        limit: Int,
        name: String?,
        gender: String?,
        race: String?,
    ): Flow<Resource<List<Character>>>
    fun getCharacterDetail(id: Int): Flow<Resource<Character>>
}