package dev.farouk.depensy.core.data.db

import androidx.room.*
import java.util.*

typealias ExpenseId = Int

@Entity(tableName = "expensesfts")
@Fts4
@TypeConverters(Converters::class)
data class ExpenseFtsEntity (
        @PrimaryKey(autoGenerate = true)
        var expenseId: ExpenseId,

        @ColumnInfo(name = "expense_value")
        val value: Double,

        @ColumnInfo(name = "expense_description")
        val description: String,

        @ColumnInfo(name = "expense_date")
        val date: Date?
)