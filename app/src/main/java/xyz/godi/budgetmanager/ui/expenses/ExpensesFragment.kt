package xyz.godi.budgetmanager.ui.expenses


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import xyz.godi.budgetmanager.R
import xyz.godi.budgetmanager.data.ExpenseDatabase
import xyz.godi.budgetmanager.databinding.FragmentExpensesBinding
import xyz.godi.budgetmanager.factory.ExpensesViewModelFactory

/**
 * Expense [Fragment] class.
 */
class ExpensesFragment : Fragment() {

    private lateinit var viewModel: ExpensesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentExpensesBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_expenses, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = ExpenseDatabase.getInstance(application).expenseDao

        val viewModelFactory = ExpensesViewModelFactory(application, dataSource)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ExpensesViewModel::class.java)

        binding.expensesViewModel = viewModel

        // set lifecycle Owner
        binding.lifecycleOwner = this

        return binding.root
    }

}