package xyz.godi.budgetmanager.db

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.godi.expenses.data.Expense
import xyz.godi.expenses.db.DailyExpenseDao

@Database(
        entities = [Expense::class],
        version = 1,
        exportSchema = false
)
abstract class ExpensesDb: RoomDatabase() {

    abstract fun dailyExpenseDao(): DailyExpenseDao

    companion object {
        const val DATABASE_NAME = "expenses_manager.db"
    }
}