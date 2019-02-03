package xyz.godi.expenses.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.godi.expenses.data.Expense

@Dao
interface DailyExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expense: List<Expense>)

    @Query("SELECT * FROM expenses")
    fun load(expense: Expense): LiveData<List<Expense>>
}