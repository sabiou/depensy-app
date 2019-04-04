package xyz.godi.budgetmanager.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import xyz.godi.budgetmanager.R
import xyz.godi.budgetmanager.adapters.ExpensesAdapter
import xyz.godi.budgetmanager.data.ExpenseDatabase
import xyz.godi.budgetmanager.databinding.FragmentHomeBinding
import xyz.godi.budgetmanager.factory.HomeViewModelFactory

/**
 * Home [Fragment] class.
 */
class HomeFragment : Fragment() {

    // adapter
    private lateinit var adapter: ExpensesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application

        val datasource = ExpenseDatabase.getInstance(application).expenseDao

        // viewModel provider
        val viewModelFactory = HomeViewModelFactory(application, datasource)

        // viewModel
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        // set the viewModel
        binding.homeViewModel = viewModel

        // set the lifecycle owner
        binding.lifecycleOwner = this

        binding.addExpenseFab.setOnClickListener {
            this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToNewExpenseFragment()
            )
        }

        return binding.root
    }
}