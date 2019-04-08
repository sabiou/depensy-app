package dev.farouk.depensy.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.farouk.depensy.data.ExpenseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeViewModel(application: Application, expensesDao: ExpenseDao): AndroidViewModel(application) {

    //repository
    //private val repository = ExpensesRepository(application, expensesDao)

    // viewModel Job for canceling coroutines
    private val viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // encapsulated navigateToAddExpense variable
    private val _navigateToAddExpense = MutableLiveData<Boolean>()
    val navigateToAddExpense: LiveData<Boolean>
        get() = _navigateToAddExpense

    // called when navigating is done
    fun doneNavigating() {
        _navigateToAddExpense.value = null
    }

    // Executes when fragment_add_expense expense button is cliked
    fun goToAddExpense() {
        _navigateToAddExpense.value = true
    }

    // OVERRIDE
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}