package dev.ranga.hpcharacters.ui.theme.common

import dev.ranga.hpcharacters.ui.common.formatDateToddMMMyy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ExtensionsText {

    @Test
    fun `formatToddMMMyy should format valid date string correctly`() {
        val dateString = "25-12-2023"
        val expectedFormattedDate = "25 Dec 23"
        val actualFormattedDate = dateString.formatDateToddMMMyy()
        assertEquals(expectedFormattedDate, actualFormattedDate)
    }

    @Test
    fun `formatToddMMMyy should return empty for invalid date string`() {
        val dateString = "invalid-date"
        val actualFormattedDate = dateString.formatDateToddMMMyy()
        assertEquals("", actualFormattedDate)
    }

    @ParameterizedTest
    @ValueSource(strings = ["25-12-2023", "01-01-2024", "15-06-2025"])
    fun `formatToddMMMyy should format various valid date strings correctly`(dateString: String) {
        val actualFormattedDate = dateString.formatDateToddMMMyy()
        when (dateString) {
            "25-12-2023" -> assertEquals("25 Dec 23", actualFormattedDate)
            "01-01-2024" -> assertEquals("01 Jan 24", actualFormattedDate)
            "15-06-2025" -> assertEquals("15 Jun 25", actualFormattedDate)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["invalid-date", "25-13-2023", "25/12/2023"])
    fun `formatToddMMMyy should return empty for various invalid date strings`(dateString: String) {
        val actualFormattedDate = dateString.formatDateToddMMMyy()
        assertEquals("", actualFormattedDate)
    }
}