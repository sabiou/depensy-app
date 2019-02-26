package xyz.godi.budgetmanager.expenses.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import xyz.godi.budgetmanager.expenses.R
import xyz.godi.budgetmanager.expenses.vo.Expense

class ExpenseListAdapter: ListAdapter<Expense, ExpenseListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ExpenseListViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.expense_item_layout, parent, false))

    override fun onBindViewHolder(holder: ExpenseListViewHolder, position: Int) {
        val expense = getItem(position) ?: return
        holder.bind(expense)
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Expense>() {
            override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean = oldItem.expenseId == newItem.expenseId

            override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean = oldItem == newItem
        }
    }
}