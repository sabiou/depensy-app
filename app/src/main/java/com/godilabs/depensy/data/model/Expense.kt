package com.godilabs.depensy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "expenses")
@TypeConverters(DateConverter::class, ExpenseCategoryConverter::class)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val category: ExpenseCategory,
    val date: Date,
    val name: String = "",
    val note: String = "",
    val budgetId: Long? = null   // FK to budgets table
)

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

class ExpenseCategoryConverter {
    @TypeConverter
    fun fromCategory(category: ExpenseCategory): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(categoryName: String): ExpenseCategory {
        return ExpenseCategory.valueOf(categoryName)
    }
}