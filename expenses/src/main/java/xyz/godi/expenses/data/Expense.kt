package xyz.godi.expenses.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense (
        @PrimaryKey
        val expenseId: Long,
        val title: String,
        val amount: Float
)