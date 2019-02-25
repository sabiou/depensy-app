package xyz.godi.budgetmanager.expenses.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import xyz.godi.budgetmanager.expenses.vo.Expense

@Dao
interface DailyExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense)
}