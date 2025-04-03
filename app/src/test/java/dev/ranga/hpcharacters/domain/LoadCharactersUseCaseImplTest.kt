package dev.ranga.hpcharacters.domain

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class LoadCharactersUseCaseImplTest {
    private val hpCharactersRepository = mockk<HpCharactersRepository>(relaxed = true)
    private val sut = LoadCharactersUseCaseImpl(hpCharactersRepository)

   @Test
   fun `should load characters`() = runTest {
       sut.load()

       coVerify { hpCharactersRepository.loadCharacters() }
   }

}