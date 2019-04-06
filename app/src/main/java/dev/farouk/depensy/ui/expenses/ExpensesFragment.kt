package dev.farouk.depensy.ui.expenses


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.farouk.depensy.adapters.ExpensesAdapter
import dev.farouk.depensy.databinding.FragmentExpensesListBinding
import dev.farouk.depensy.utils.InjectorsUtils

/**
 * Expense [Fragment] class.
 */
class ExpensesFragment : Fragment() {

    private lateinit var viewModel: ExpensesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentExpensesListBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root

        val factory = InjectorsUtils.providesExpensesListViewModelFactory(context)

        viewModel = ViewModelProviders.of(this, factory).get(ExpensesViewModel::class.java)

        // adapter
        val adapter = ExpensesAdapter()
        binding.expensesList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: ExpensesAdapter) {
        viewModel.expenses.observe(viewLifecycleOwner, Observer { expenses ->
            if (expenses != null) adapter.submitList(expenses)
        })
    }
}