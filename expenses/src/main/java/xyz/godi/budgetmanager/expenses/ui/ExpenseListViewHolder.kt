package xyz.godi.budgetmanager.expenses.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.expense_item_layout.view.*
import xyz.godi.budgetmanager.expenses.vo.Expense

class ExpenseListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(expense: Expense) {
        // the desxription of the expense
        itemView.expenseName.text = expense.description

        // the amount of the expense
        itemView.expenseAmount.text = expense.amount.toString()
    }
}