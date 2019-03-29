package xyz.godi.budgetmanager.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.godi.budgetmanager.data.repository.ExpensesRepository
import xyz.godi.budgetmanager.screens.expenses.ExpensesViewModel
import java.lang.IllegalArgumentException
/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides context to the ViewModel.
 */
@Suppress("UNCHECKED_CAST")
class ExpensesViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesViewModel::class.java)) {
            return ExpensesViewModel(application) as T
        }
        // else, throw an exception
        throw IllegalArgumentException("Unknow viewModel class")
    }
}