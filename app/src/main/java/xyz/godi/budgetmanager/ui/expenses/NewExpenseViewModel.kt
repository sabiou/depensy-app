package xyz.godi.budgetmanager.ui.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import xyz.godi.budgetmanager.data.repository.ExpensesRepository
import xyz.godi.budgetmanager.vo.Expense

class NewExpenseViewModel(
        private val repository: ExpensesRepository) : ViewModel() {

    /**
     *  /**
     * Execute when the add button is clicked
    */
     */
    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repository
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