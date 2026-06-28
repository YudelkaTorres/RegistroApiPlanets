package edu.ucne.registroapiplanets.data.remote.dto

import edu.ucne.registroapiplanets.domain.model.Character

data class CharacterResponseDto(
    val items: List<CharacterDto>
)

data class CharacterDto(
    val id: Int,
    val name: String,
    val ki: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String,
    val maxKi: String
) {
    fun toDomain() = Character(
        id = id,
        name = name,
        ki = ki,
        race = race,
        gender = gender,
        description = description,
        image = image,
        maxKi = maxKi,
    )
}