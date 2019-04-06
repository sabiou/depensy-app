package dev.farouk.depensy.ui.stats


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import dev.farouk.depensy.R
import dev.farouk.depensy.databinding.FragmentStatsBinding

class StatsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // inflate the fragment using data binding
        val binding: FragmentStatsBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_stats, container, false
        )

        return binding.root
    }
}
