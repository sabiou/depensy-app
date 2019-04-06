package dev.farouk.depensy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.farouk.depensy.data.Expense
import dev.farouk.depensy.databinding.ExpenseItemLayoutBinding

class ExpensesAdapter: ListAdapter<Expense, ExpensesAdapter.ExpensesViewHolder>(ExpenseDiffCallback()) {

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        val expense = getItem(position)
        holder.apply {
            bind(expense)
        }
    }

    // OVERRIDE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {

        return ExpensesViewHolder(ExpenseItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    class ExpensesViewHolder(
            private val binding: ExpenseItemLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {

        // bind
        fun bind(item: Expense) {
            binding.apply {
                expense = item
                executePendingBindings()
            }
        }
    }
}

private class ExpenseDiffCallback: DiffUtil.ItemCallback<Expense>() {
    override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean = oldItem.expenseId == newItem.expenseId

    override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean = oldItem == newItem
}