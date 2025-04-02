package dev.ranga.hpcharacters.data.remote

import dev.ranga.hpcharacters.data.model.HpCharacterDto
import retrofit2.http.GET

interface HpCharacterService {
    @GET("/api/characters")
    suspend fun getAllCharacters(): List<HpCharacterDto>

    companion object {
        const val BASE_URL = "https://hp-api.onrender.com"
    }
}