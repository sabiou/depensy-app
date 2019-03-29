package xyz.godi.budgetmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.expense_item_layout.view.*
import xyz.godi.budgetmanager.R
import xyz.godi.budgetmanager.vo.Expense

class ExpensesAdapter: RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder>() {

    private var expenses: List<Expense> = ArrayList()

    // OVERRIDE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.expense_item_layout, parent, false)

        return ExpensesViewHolder(itemView)
    }

    override fun getItemCount(): Int = expenses.size

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        val currentExpense = expenses[position]
        // bind via bind() method
        holder.bind(currentExpense)
    }


    inner class ExpensesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val tv_description = itemView.expenseName
        val tv_price = itemView.expenseAmount

        private var expense: Expense? = null
        private var view: View = itemView

        // bind
        fun bind(expense: Expense) {
            this.expense = expense
            view.expenseName.text = expense.description
            view.expenseAmount.text = expense.amount.toString()
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            TODO("not implemented")
        }
    }

    companion object {
        private val EXPENSE_KEY = "EXPENSE"
    }
}