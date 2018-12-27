package xyz.godi.budgetmanager


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import xyz.godi.budgetmanager.databinding.FragmentCreateBudgetBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class CreateBudgetFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil
                .inflate<FragmentCreateBudgetBinding>(inflater, R.layout.fragment_create_budget,
                        container, false)

        // onclick listener for create budget button
        binding.addBudgetButton.setOnClickListener { v: View ->
            // navigate to homeActivity
        }

        return binding.root
    }

}
