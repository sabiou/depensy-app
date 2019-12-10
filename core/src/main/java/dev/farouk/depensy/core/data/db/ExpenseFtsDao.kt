package dev.farouk.depensy.core.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * DAO for the [ExpenseFtsEntity] class.
 */
@Dao
interface ExpenseFtsDao {

    // insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expense: ExpenseFtsEntity)
    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param expense new value to write
     */
    @Update
    fun update(expense: ExpenseFtsEntity)
    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM expensesfts ORDER BY expenseId DESC")
    fun getAllExpenses(): LiveData<List<ExpenseFtsEntity>>

    /**
     * Full Text search
     */
    @Query("SELECT expenseId FROM expensesfts WHERE expensesfts MATCH :query")
    fun searchAllExpenses(query: String): List<String>

    /**
     * Selects and returns the three latest expenses.
     */
    @Query("SELECT * FROM expensesfts ORDER BY expenseId DESC LIMIT 3")
    fun getLastThreeExpenses(): LiveData<List<ExpenseFtsEntity>>
}