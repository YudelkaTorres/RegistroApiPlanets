package edu.ucne.registroapiplanets.data.repository

import edu.ucne.registroapiplanets.data.remote.Resource
import edu.ucne.registroapiplanets.data.remote.remotedatasource.CharacterRemoteDataSource
import edu.ucne.registroapiplanets.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import edu.ucne.registroapiplanets.domain.model.Character
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {
    override fun getCharacters(
        page: Int,
        limit: Int,
        name: String?,
        gender: String?,
        race: String?
    ): Flow<Resource<List<Character>>> = flow {

        emit(Resource.Loading())

        val response = remoteDataSource.getCharacters(page, limit, name, gender, race)
        response.onSuccess { characters ->
            emit(Resource.Success(characters.items.map { it.toDomain() }))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }

    override fun getCharacterDetail(id: Int): Flow<Resource<Character>> = flow {
        emit(Resource.Loading())

        val response = remoteDataSource.getCharacterDetail(id)
        response.onSuccess { characters ->
            emit(Resource.Success(characters.toDomain() ))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }
}
