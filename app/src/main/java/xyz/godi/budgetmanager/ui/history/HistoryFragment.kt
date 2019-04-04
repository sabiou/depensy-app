package xyz.godi.budgetmanager.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import xyz.godi.budgetmanager.R
import xyz.godi.budgetmanager.databinding.FragmentHistoryBinding
import xyz.godi.budgetmanager.factory.HistoryViewModelFactory

class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // inflate the layout for this fragment
        val binding: FragmentHistoryBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_history, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = HistoryViewModelFactory(application)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HistoryViewModel::class.java)

        binding.historyViewModel = viewModel

        // set the lifecycle owner
        binding.lifecycleOwner = this

        return binding.root
    }
}