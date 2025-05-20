package dev.ranga.hpcharacters.ui.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun String.formatDateToddMMMyy(): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val date = LocalDate.parse(this, inputFormatter)

        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        date.format(outputFormatter)
    } catch (e: DateTimeParseException) {
        // Handle parsing error
        ""
    }
}

fun String.containsQuery(query: String): Boolean = contains(query, ignoreCase = true)

