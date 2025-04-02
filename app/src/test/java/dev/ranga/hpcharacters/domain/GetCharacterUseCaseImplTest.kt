package dev.ranga.hpcharacters.domain

import dev.ranga.hpcharacters.testFixtures.Fixtures.hpCharacter1
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class GetCharacterUseCaseImplTest {
    private val repository: HpCharactersRepository = mockk(relaxed = true)
    private val sut = GetCharacterUseCaseImpl(repository)

    @Test
    fun `should get character`() = runTest {
        val expected = hpCharacter1

        coEvery { repository.getCharacterById(any()) } returns expected

        val result = sut.getById("1")

        assertEquals(expected, result)
    }
}