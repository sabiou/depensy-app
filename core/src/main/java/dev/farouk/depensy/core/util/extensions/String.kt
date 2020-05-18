package dev.farouk.depensy.core.util.extensions

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by Farouk on 18/05/20.
 */

// Parsing

fun String.toLocalDate(pattern: String): LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))