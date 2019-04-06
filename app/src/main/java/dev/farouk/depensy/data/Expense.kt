package dev.farouk.depensy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses_table")
data class Expense (
        @PrimaryKey(autoGenerate = true)
        var expenseId: Long = 0L,

        @ColumnInfo(name = "description_expense")
        val description: String = "",

        @ColumnInfo(name = "expense_amount")
        val amount: Int = 0,

        @ColumnInfo(name = "date_and_time")
        val dateAndTime: Long = System.currentTimeMillis()
)