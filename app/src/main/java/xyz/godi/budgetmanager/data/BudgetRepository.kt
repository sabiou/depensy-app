package xyz.godi.budgetmanager.data

class BudgetRepository private constructor(private val budgetDAO: BudgetDAO) {

    fun getBudgets() = budgetDAO.getBudgets()

    fun getBudget(budgetId: String) = budgetDAO.getBudget(budgetId)


    companion object {
        // for singleton instanciation
        @Volatile private var instance: BudgetRepository? = null

        fun getInstance(budgetDAO: BudgetDAO) =
                instance?: synchronized(this) {
                    instance?: BudgetRepository(budgetDAO).also { instance = it }
                }
    }
}