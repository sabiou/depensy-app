package com.godilabs.depensy.data.dao

import androidx.room.*
import com.godilabs.depensy.data.model.Budget
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: Budget)

    @Update
    suspend fun updateBudget(budget: Budget)

    @Delete
    suspend fun deleteBudget(budget: Budget)

    @Query("SELECT * FROM budgets WHERE id = :id")
    fun getBudgetById(id: Long): Flow<Budget?>

    @Query("SELECT * FROM budgets")
    fun getAllBudgets(): Flow<List<Budget>>
}