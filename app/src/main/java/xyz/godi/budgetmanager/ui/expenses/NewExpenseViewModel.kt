package xyz.godi.budgetmanager.ui.expenses

import android.app.Application
import androidx.lifecycle.*
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import xyz.godi.budgetmanager.data.ExpenseDao
import xyz.godi.budgetmanager.vo.Expense

class NewExpenseViewModel(
        private val database: ExpenseDao,
        application: Application): AndroidViewModel(application) {

    private var _expenseDescription = MutableLiveData<TextInputEditText>()
    val expenseDescription: LiveData<TextInputEditText> get() = _expenseDescription

    private var _amount = MutableLiveData<TextInputEditText>()
    val amount: LiveData<TextInputEditText> get() = _amount

    private var _expense = MutableLiveData<Expense>()
    val expense: LiveData<Expense> get() = _expense

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()
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

    // insert
    private suspend fun insert(expense: Expense) {
        withContext(Dispatchers.IO) {
            database.insert(expense)
        }
    }

    // add expense button state
    val isAddExpenseButtonVisible = _expenseDescription.value != null && _amount.value != null // not sure if this work

    /**
     * Execute when the add button is clicked
     */
    fun addExpense() {
        uiScope.launch {
            // Create a new expense, from the form fields,
            // and insert it into the database.
            val description = _expenseDescription.value.toString()
            val amountValue = _amount.value
            val newExpense = Expense(description = description.trim(),
                    amount = amountValue!!.text.toString().toFloat())

            insert(newExpense)

            _expense.value = newExpense
        }
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}