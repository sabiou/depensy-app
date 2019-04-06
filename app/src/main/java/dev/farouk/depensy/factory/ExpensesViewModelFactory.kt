package dev.farouk.depensy.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.farouk.depensy.data.repository.ExpensesRepository
import dev.farouk.depensy.ui.expenses.ExpensesViewModel

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides context to the ViewModel.
 */
@Suppress("UNCHECKED_CAST")
class ExpensesViewModelFactory(val repository: ExpensesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesViewModel::class.java)) {
            return ExpensesViewModel(repository) as T
        }
        // else, throw an exception
        throw IllegalArgumentException("Unknown viewModel class")
    }
}