package dev.farouk.depensy.ui.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.farouk.depensy.data.Expense
import dev.farouk.depensy.data.repository.ExpensesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NewExpenseViewModel(
        private val repository: ExpensesRepository) : ViewModel() {

    /**
     *  /**
     * Execute when the add button is clicked
    */
     */
    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repository.insertExpense(expense)
        }
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}