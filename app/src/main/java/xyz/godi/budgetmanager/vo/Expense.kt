package xyz.godi.budgetmanager.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses_table")
data class Expense (
        @PrimaryKey(autoGenerate = true)
        val expenseId: Long = 0L,

        @ColumnInfo(name = "description_expense")
        val description: String,

        @ColumnInfo(name = "expense_amount")
        val amount: Float
)