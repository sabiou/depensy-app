package dev.farouk.depensy.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.farouk.depensy.core.util.extensions.toExactDouble
import dev.farouk.depensy.core.util.extensions.toExactFloat
import dev.farouk.depensy.data.models.Expense
import dev.farouk.depensy.utils.getCurrentTimestamp
import org.threeten.bp.LocalDate

/**
 * Created by Farouk on 16/05/20.
 */
@Entity(tableName = "expenses")
data class ExpenseEntity(
        @PrimaryKey(autoGenerate = true) val id: Long,
        @ColumnInfo(name = "amount") val amount: Float,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "date") val date: LocalDate,
        @ColumnInfo(name = "notes") val notes: String,
        @ColumnInfo(name = "created_at") val createdAt: Long,
        @ColumnInfo(name = "modified_at") val modifiedAt: Long
) {

    fun mapToExpense() =
            Expense(
                    id = id.toString(),
                    amount = amount.toExactDouble(),
                    title = title,
                    date = date,
                    notes = notes,
                    timestamp = createdAt
            )

    companion object {

        fun prepareForInsertion(expense: Expense) =
                ExpenseEntity(
                        id = 0,
                        amount = expense.amount.toExactFloat(),
                        title = expense.title,
                        date = expense.date,
                        notes = expense.notes,
                        createdAt = getCurrentTimestamp(),
                        modifiedAt = 0L
                )

        fun prepareForUpdate(expense: Expense) =
                ExpenseEntity(
                        id = expense.id.toLong(),
                        amount = expense.amount.toExactFloat(),
                        title = expense.title,
                        date = expense.date,
                        notes = expense.notes,
                        createdAt = expense.timestamp ?: 0L,
                        modifiedAt = 0L
                )

        fun fromExpense(expense: Expense) =
                ExpenseEntity(
                        id = expense.id.toLong(),
                        amount = expense.amount.toExactFloat(),
                        title = expense.title,
                        date = expense.date,
                        notes = expense.notes,
                        createdAt = expense.timestamp ?: 0L,
                        modifiedAt = 0L
                )
    }
}