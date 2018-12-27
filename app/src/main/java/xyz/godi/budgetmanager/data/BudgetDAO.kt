package xyz.godi.budgetmanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/**
 * Data Access Object for the Budget class
 */
@Dao
interface BudgetDAO {

    // select all budget from database
    @Query("SELECT * FROM budget ORDER BY name")
    fun getBudgets(): LiveData<List<Budget>>

    // select by id
    @Query("SELECT * FROM budget WHERE id= :budgetId")
    fun getBudget(budgetId: String) : LiveData<Budget>
}