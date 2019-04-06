package dev.farouk.depensy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "expenses_table")
data class Expense(
        @PrimaryKey(autoGenerate = true)
        var expenseId: Int,

        @ColumnInfo(name = "expense_value")
        val value: Double,

        @ColumnInfo(name = "expense_date")
        val date: Date?,

        @ColumnInfo(name = "expense_category")
        val category: Category
)