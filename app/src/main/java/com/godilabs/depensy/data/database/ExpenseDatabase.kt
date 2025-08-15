package com.godilabs.depensy.data.database

import androidx.room.*
import android.content.Context
import com.godilabs.depensy.data.dao.BudgetDao
import com.godilabs.depensy.data.dao.ExpenseDao
import com.godilabs.depensy.data.model.*

@Database(
    entities = [Expense::class, Budget::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(DateConverter::class, ExpenseCategoryConverter::class, BudgetPeriodConverter::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun budgetDao(): BudgetDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}