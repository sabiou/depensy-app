package dev.farouk.depensy.ui.expenses


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import dev.farouk.depensy.R
import dev.farouk.depensy.data.models.Expense
import dev.farouk.depensy.databinding.FragmentAddExpenseBinding
import dev.farouk.depensy.factory.NewExpenseViewModelFactory
import dev.farouk.depensy.utils.InjectorsUtils
import kotlinx.android.synthetic.main.fragment_add_expense.*

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
            val description = expenseDescriptionEdt.text.toString()
            val value = expenseValueEdt.text.toString().toDouble()
            val expense = Expense(description = description, value = value, date = null, expenseId = 222)
            viewModel.addExpense(expense)
            // show snackbar
            Snackbar.make(view, "Expense $description added", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }
}