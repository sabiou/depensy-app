package xyz.godi.budgetmanager.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.godi.budgetmanager.data.ExpenseDao
import xyz.godi.budgetmanager.ui.expenses.ExpensesViewModel
import xyz.godi.budgetmanager.ui.expenses.NewExpenseViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class NewExpenseViewModelFactory(private val application: Application,
                                 private val database: ExpenseDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewExpenseViewModel::class.java)) {
            return ExpensesViewModel(application, database) as T
        }
        // else, throw an exception
        throw IllegalArgumentException("Unknow viewModel class")
    }
}