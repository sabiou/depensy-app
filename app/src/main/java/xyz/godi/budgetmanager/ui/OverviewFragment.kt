package xyz.godi.budgetmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import xyz.godi.budgetmanager.screens.expenses.ExpensesViewModel

class OverviewFragment : Fragment() {

    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ExpensesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        viewModel = ViewModelProviders.of(this, viewModelFactory)
//                .get(ExpensesViewModel::class.java)

        return null
    }
}