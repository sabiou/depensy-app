package xyz.godi.budgetmanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/**
 * Data Access Object for the Budget class
 */
@Dao
interface BudgetDAO {

    @Query("SELECT * FROM budget ORDER BY name")
    fun getBudgets(): LiveData<List<Budget>>
}