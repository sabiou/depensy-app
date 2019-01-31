package xyz.godi.budgetmanager


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import xyz.godi.budgetmanager.databinding.FragmentWelcomeBinding

import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

/**
 * A simple [Fragment] subclass.
 */
class WelcomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout using data binding
        val binding = DataBindingUtil
                        .inflate<FragmentWelcomeBinding>(inflater, R.layout.fragment_welcome, container,
                                false)

        // set create budget onClick listener
        binding.createBudgetButton.setOnClickListener { v: View ->
            // navigate to create budget fragment
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_overall, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController()) ||
                super.onOptionsItemSelected(item)
    }
}