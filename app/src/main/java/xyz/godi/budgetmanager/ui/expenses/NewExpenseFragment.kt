package xyz.godi.budgetmanager.ui.expenses


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import xyz.godi.budgetmanager.R
import xyz.godi.budgetmanager.data.ExpenseDatabase
import xyz.godi.budgetmanager.databinding.FragmentAddExpenseBinding
import xyz.godi.budgetmanager.factory.NewExpenseViewModelFactory

class NewExpenseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentAddExpenseBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_add_expense, container, false)

        val application = requireNotNull(this.activity!!).application

        val datasource = ExpenseDatabase.getInstance(application).expenseDao

        val viewModelFactory = NewExpenseViewModelFactory(application, datasource)

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewExpenseViewModel::class.java)

        // set the view viewModel
        binding.newExpenseViewModel = viewModel

        // set the view lifecycle owner
        binding.lifecycleOwner = this

        // observer for fields
        viewModel.expenseDescription.observe(this, Observer {
            if (it.text.isNullOrEmpty()) {
                it.error = "Enter a valid description"
                it.requestFocus()
            }
        })

        viewModel.amount.observe(this, Observer {
            if (it.text.isNullOrEmpty()) {
                it.error = "Invalid amount"
                it.requestFocus()
            }
        })

        return binding.root
    }
}