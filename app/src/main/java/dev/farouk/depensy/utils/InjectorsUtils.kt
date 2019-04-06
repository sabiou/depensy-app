package dev.farouk.depensy.utils

import android.content.Context
import dev.farouk.depensy.data.ExpenseDatabase
import dev.farouk.depensy.data.repository.ExpensesRepository
import dev.farouk.depensy.factory.ExpensesViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorsUtils {

    fun getExpenseRepository(context: Context): ExpensesRepository {
        return ExpensesRepository.getInstance(
                ExpenseDatabase.getInstance(context.applicationContext).expenseDao())
    }

    // provides...
    fun providesExpensesListViewModelFactory(context: Context): ExpensesViewModelFactory {
        val repository = getExpenseRepository(context)
        return ExpensesViewModelFactory(repository)
    }
}