package dev.ranga.hpcharacters.data.remote

import dev.ranga.hpcharacters.data.model.CharacterDto
import retrofit2.http.GET

interface HpCharacterService {
    @GET("/api/characters")
    suspend fun getAllCharacters(): List<CharacterDto>

    companion object {
        const val BASE_URL = "https://hp-api.onrender.com"
    }
}