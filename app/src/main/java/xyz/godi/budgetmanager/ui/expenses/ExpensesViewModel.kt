package xyz.godi.budgetmanager.ui.expenses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import xyz.godi.budgetmanager.data.ExpenseDao
import xyz.godi.budgetmanager.data.repository.ExpensesRepository
import xyz.godi.budgetmanager.vo.Expense

class ExpensesViewModel(application: Application, val expenseDao: ExpenseDao): AndroidViewModel(application) {

    /**
     * Repository for our expense database
     */
    private var repository = ExpensesRepository(application, expenseDao)

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    suspend fun getAllExpenses(): LiveData<List<Expense>> {
        return withContext(Dispatchers.IO) {
            repository.loadExpenses()
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