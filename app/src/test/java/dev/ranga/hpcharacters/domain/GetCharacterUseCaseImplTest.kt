package dev.ranga.hpcharacters.domain

import dev.ranga.hpcharacters.testFixtures.Fixtures.hpCharacter1
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

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

    @Test
    fun `getById repository failure`() = runTest {

        coEvery { repository.getCharacterById(any()) } throws Exception()

        assertThrows<Exception> {
            sut.getById("1")
        }
    }

    @Test
    fun `getById empty string id`() = runTest {

        coEvery { repository.getCharacterById("") } throws Exception("Character not found")

        assertThrows<Exception> {
            sut.getById("")
        }
    }

    @Test
    fun `getById non existing id`() = runTest {

        coEvery { repository.getCharacterById("100") } throws Exception("Character not found")

        assertThrows<Exception> {
            sut.getById("100")
        }
    }
}