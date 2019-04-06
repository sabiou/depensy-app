package dev.farouk.depensy.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import dev.farouk.depensy.R
import dev.farouk.depensy.adapters.ExpensesAdapter
import dev.farouk.depensy.data.ExpenseDatabase
import dev.farouk.depensy.databinding.FragmentHomeBinding
import dev.farouk.depensy.factory.HomeViewModelFactory

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

        val datasource = ExpenseDatabase.getInstance(application).expenseDao()

        // viewModel provider
        val viewModelFactory = HomeViewModelFactory(application, datasource)

        // viewModel
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        // set the viewModel
        binding.homeViewModel = viewModel

        // set the lifecycle owner
        binding.lifecycleOwner = this

        binding.buttonSeeAllExpenses.setOnClickListener {
            this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToExpensesFragment()
            )
        }

        binding.addExpenseFab.setOnClickListener {
            this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToNewExpenseFragment()
            )
        }

        return binding.root
    }
}