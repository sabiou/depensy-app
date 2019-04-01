package xyz.godi.budgetmanager.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.godi.budgetmanager.data.ExpenseDao
import xyz.godi.budgetmanager.ui.history.HistoryViewModel
import xyz.godi.budgetmanager.ui.home.HomeViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(val application: Application,
                           private val expenseDao: ExpenseDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HomeViewModel(application,expenseDao) as T
        }
        // else, throw an exception
        throw IllegalArgumentException("Unknow viewModel class")
    }
}