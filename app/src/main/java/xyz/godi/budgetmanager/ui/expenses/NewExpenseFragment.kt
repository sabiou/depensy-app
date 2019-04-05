package xyz.godi.budgetmanager.ui.expenses


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_expense.*
import kotlinx.android.synthetic.main.fragment_home.*
import xyz.godi.budgetmanager.R
import xyz.godi.budgetmanager.data.ExpenseDatabase
import xyz.godi.budgetmanager.databinding.FragmentAddExpenseBinding
import xyz.godi.budgetmanager.factory.NewExpenseViewModelFactory
import xyz.godi.budgetmanager.utils.InjectorsUtils
import xyz.godi.budgetmanager.vo.Expense

class NewExpenseFragment : Fragment() {

    lateinit var viewModel: NewExpenseViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentAddExpenseBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_add_expense, container, false)

        val viewModelFactory = NewExpenseViewModelFactory(InjectorsUtils.getExpenseRepository(requireActivity()))

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewExpenseViewModel::class.java)

        // set the view viewModel
        binding.newExpenseViewModel = viewModel

        // set the view lifecycle owner
        binding.lifecycleOwner = this

        binding.buttonAddNewExpense.setOnClickListener { view ->
            val description = descriptionEdtxt.text.toString()
            val amount = Integer.parseInt(amountEdtxt.text.toString())
            val expense = Expense(description = description, amount = amount)
            viewModel.addExpense(expense)
            // show Snackbar
            Snackbar.make(view, "Expense $description added", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }
}