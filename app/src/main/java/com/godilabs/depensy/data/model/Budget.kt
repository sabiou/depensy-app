package com.godilabs.depensy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val amount: Double,
    val period: BudgetPeriod?,
    val category: ExpenseCategory?,
    val startDate: Long?
)

enum class BudgetPeriod(val displayName: String) {
    DAILY("Journalier"),
    WEEKLY("Hebdomadaire"),
    MONTHLY("Mensuel"),
    YEARLY("Annuel")
}