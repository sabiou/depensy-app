package xyz.godi.budgetmanager.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.godi.budgetmanager.screens.expenses.ExpensesViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class OverviewViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesViewModel::class.java)) {
            return ExpensesViewModel(application) as T
        }
        // else, throw an exception
        throw IllegalArgumentException("Unknow viewModel class")
    }
}