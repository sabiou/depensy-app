package dev.farouk.depensy.ui.expenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.farouk.depensy.data.models.Expense
import dev.farouk.depensy.data.repository.ExpensesRepository

/**
 * The viewModel for [ExpensesFragment]
 */
class ExpensesViewModel internal constructor(expensesRepository: ExpensesRepository): ViewModel() {

    val expenses: LiveData<List<Expense>> = expensesRepository.getExpenses()
}