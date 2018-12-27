package xyz.godi.budgetmanager


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import xyz.godi.budgetmanager.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout using data binding
        val binding = DataBindingUtil
                        .inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container,
                                false)

        // set create budget onClick listener
        binding.createBudgetButton.setOnClickListener { v: View ->
            // navigate to create budget fragment
        }

        return binding.root
    }
}