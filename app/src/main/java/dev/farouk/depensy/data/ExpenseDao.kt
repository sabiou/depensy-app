package dev.farouk.depensy.data

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.farouk.depensy.data.models.Expense

/**
 * Defines methods for using the Expense class with Room.
 */
@Dao
interface ExpenseDao {

    // insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
     * Selects and returns the three latest expenses.
     */
    @Query("SELECT * FROM expenses_table ORDER BY expenseId DESC LIMIT 3")
    fun getLastThreeExpenses(): LiveData<List<Expense>>
}