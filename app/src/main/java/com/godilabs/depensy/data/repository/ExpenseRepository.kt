package com.godilabs.depensy.data.repository

import com.godilabs.depensy.data.dao.ExpenseDao
import com.godilabs.depensy.data.model.Budget
import com.godilabs.depensy.data.model.Expense
import com.godilabs.depensy.data.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow
import java.util.Date

class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {
    fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()

    suspend fun getExpenseById(id: Long): Expense? = expenseDao.getExpenseById(id)

    fun getExpensesByCategory(category: ExpenseCategory): Flow<List<Expense>> =
        expenseDao.getExpensesByCategory(category)

    fun getExpensesByDateRange(startDate: Date, endDate: Date): Flow<List<Expense>> =
        expenseDao.getExpensesByDateRange(startDate, endDate)

    fun getTotalAmount(): Flow<Double?> = expenseDao.getTotalAmount()

    fun getTotalAmountByCategory(category: ExpenseCategory): Flow<Double?> =
        expenseDao.getTotalAmountByCategory(category)

    suspend fun insertExpense(expense: Expense): Long = expenseDao.insert(expense)

    suspend fun updateExpense(expense: Expense) = expenseDao.updateExpense(expense)

    suspend fun deleteExpense(expense: Expense) = expenseDao.deleteExpense(expense)

    suspend fun deleteExpenseById(id: Long) = expenseDao.deleteExpenseById(id)

    suspend fun addExpense(
        amount: Double,
        category: ExpenseCategory,
        date: Date,
        name: String,
        note: String,
        budget: Budget?
    ) {
        expenseDao.insert(
            Expense(
                amount = amount,
                category = category,
                date = date,
                name = name,
                note = note,
                budgetId = budget?.id
            )
        )
    }
}