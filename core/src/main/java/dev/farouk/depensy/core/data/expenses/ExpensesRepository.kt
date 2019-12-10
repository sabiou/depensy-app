package dev.farouk.depensy.core.data.expenses

import dev.farouk.depensy.core.data.db.ExpenseFtsDao
import dev.farouk.depensy.core.data.db.ExpenseId
import javax.inject.Inject

class ExpensesRepository @Inject constructor(private val expensesDao: ExpenseFtsDao) {

    fun getExpenses() = expensesDao.getAllExpenses()
}