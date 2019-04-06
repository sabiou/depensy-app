package dev.farouk.depensy.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.farouk.depensy.data.repository.ExpensesRepository
import dev.farouk.depensy.ui.expenses.NewExpenseViewModel

@Suppress("UNCHECKED_CAST")
class NewExpenseViewModelFactory(private val expensesRepository: ExpensesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewExpenseViewModel::class.java)) {
            return NewExpenseViewModel(expensesRepository) as T
        }
        // else, throw an exception
        throw IllegalArgumentException("Unknow viewModel class")
    }
}