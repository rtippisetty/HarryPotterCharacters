package dev.ranga.hpcharacters.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.ranga.hpcharacters.domain.GetCachedCharactersUseCaseImpl
import dev.ranga.hpcharacters.domain.GetCharacterUseCaseImpl
import dev.ranga.hpcharacters.domain.LoadCharactersUseCaseImpl
import dev.ranga.hpcharacters.exposedApi.GetCachedCharactersUseCase
import dev.ranga.hpcharacters.exposedApi.GetCharacterUseCase
import dev.ranga.hpcharacters.exposedApi.LoadCharactersUseCase

@Module
@InstallIn(ViewModelComponent::class)
interface HpCharacterDomainModule {
    @Binds
    fun provideGetCachedCharactersUseCase(
        impl: GetCachedCharactersUseCaseImpl
    ): GetCachedCharactersUseCase

    @Binds
    fun provideGetCharacterUseCase(
        impl: GetCharacterUseCaseImpl
    ): GetCharacterUseCase

    @Binds
    fun provideLoadCharactersUseCase(
        impl: LoadCharactersUseCaseImpl
    ): LoadCharactersUseCase

}