package xyz.godi.budgetmanager


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import xyz.godi.budgetmanager.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil
                .inflate<FragmentAboutBinding>(inflater, R.layout.fragment_about, container,
                        false)

        return binding.root
    }

}