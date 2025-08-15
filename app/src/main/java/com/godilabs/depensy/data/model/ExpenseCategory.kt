package com.godilabs.depensy.data.model

import java.util.Date

enum class ExpenseCategory(val displayName: String) {
    FOOD("Alimentation"),
    TRANSPORT("Transport"),
    ENTERTAINMENT("Divertissement"),
    SHOPPING("Shopping"),
    HEALTH("Santé"),
    EDUCATION("Éducation"),
    BILLS("Factures"),
    OTHER("Autre")
}