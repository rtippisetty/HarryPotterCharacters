package dev.ranga.hpcharacters.api

fun interface LoadCharactersUseCase {
    suspend fun load()
}