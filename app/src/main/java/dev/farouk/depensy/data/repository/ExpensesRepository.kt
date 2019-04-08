package dev.farouk.depensy.data.repository

import dev.farouk.depensy.data.models.Expense
import dev.farouk.depensy.data.ExpenseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExpensesRepository private constructor(private val expenseDao: ExpenseDao) {
    /**
     * Repository module for handling data operations.
     */

    //load all expenses
    fun getExpenses() = expenseDao.getAllExpenses()

    // get latest three expenses
    fun getLastThreeExpense() = expenseDao.getLastThreeExpenses()

    suspend fun insertExpense(expense: Expense) {
        withContext(Dispatchers.IO) {
            expenseDao.insert(expense)
        }
    }

    companion object {
        // for singleton instanciation
        @Volatile
        private var instance: ExpensesRepository? = null

        fun getInstance(expenseDao: ExpenseDao) =
                instance ?: synchronized(this) {
                    instance ?: ExpensesRepository(expenseDao).also { instance = it }
                }
    }
}