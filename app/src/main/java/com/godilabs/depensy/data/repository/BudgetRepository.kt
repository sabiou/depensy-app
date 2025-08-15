package com.godilabs.depensy.data.repository

import com.godilabs.depensy.data.dao.BudgetDao
import com.godilabs.depensy.data.model.Budget
import com.godilabs.depensy.data.model.Expense
import com.godilabs.depensy.data.model.ExpenseCategory
import com.godilabs.depensy.data.model.BudgetPeriod
import kotlinx.coroutines.flow.Flow

class BudgetRepository(private val budgetDao: BudgetDao) {
    val allBudgets: Flow<List<Budget>> = budgetDao.getAllBudgets()

    fun getBudgetById(id: Long): Flow<Budget?> {
        return budgetDao.getBudgetById(id)
    }

    suspend fun insert(budget: Budget) {
        budgetDao.insertBudget(budget)
    }

    suspend fun update(budget: Budget) {
        budgetDao.updateBudget(budget)
    }

    suspend fun delete(budget: Budget) {
        budgetDao.deleteBudget(budget)
    }
}