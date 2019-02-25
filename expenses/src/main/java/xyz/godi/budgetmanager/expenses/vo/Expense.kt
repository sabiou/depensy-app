package xyz.godi.budgetmanager.expenses.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense (
        @PrimaryKey
        val expenseId: Long,
        val description: String,
        val amount: Float
)