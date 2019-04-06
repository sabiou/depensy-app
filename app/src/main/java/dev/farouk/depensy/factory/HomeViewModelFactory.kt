package dev.farouk.depensy.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.farouk.depensy.data.ExpenseDao
import dev.farouk.depensy.ui.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(val application: Application,
                           private val expenseDao: ExpenseDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application,expenseDao) as T
        }
        // else, throw an exception
        throw IllegalArgumentException("Unknown viewModel class")
    }
}