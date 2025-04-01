package dev.ranga.hpcharacters.exposedApi

fun interface LoadCharactersUseCase {
    suspend fun load()
}