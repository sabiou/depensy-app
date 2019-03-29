package xyz.godi.budgetmanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import xyz.godi.budgetmanager.vo.Expense

/**
 * Defines methods for using the Expense class with Room.
 */
@Dao
interface ExpenseDao {

    // insert
    @Insert
    fun insert(expense: Expense)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param expense new value to write
     */
    @Update
    fun update(expense: Expense)

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM expenses_table ORDER BY expenseId DESC")
    fun getAllExpenses(): LiveData<List<Expense>>

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM expenses_table ORDER BY expenseId DESC LIMIT 1")
    fun getLatestExpense(): Expense?
}