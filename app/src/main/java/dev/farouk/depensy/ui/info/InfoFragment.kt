package dev.farouk.depensy.ui.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.farouk.depensy.R
import dev.farouk.depensy.databinding.FragmentInfoBinding
import dev.farouk.depensy.utils.EventObserver
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : Fragment() {

    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: InfoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment using databinding
        val binding: FragmentInfoBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_info, container, false)

        viewModelFactory = InfoViewModelFactory()

        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
                .get(InfoViewModel::class.java)

        binding.infoViewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModel()
        setupButtons()
    }

    private fun setupViewModel() {
        viewModel.navigateReselect.observe(this, EventObserver {
            infoScrollView.smoothScrollTo(0, 0)
            appBarLayout.setExpanded(true)
        })
    }

    private fun setupButtons() {
        shareButton.setOnClickListener {
            val appName = getString(R.string.app_name)
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_SUBJECT, requireContext().getString(R.string.subject_share, appName))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.text_share))
                type = "text/plain"
            }
            // start the sharing intent chooser
            startActivity(Intent.createChooser(intent, requireContext().getString(R.string.title_share)))
        }

        // view open sources licenses
        openSourceLicensesButton.setOnClickListener {
            val intent = Intent(requireContext(), OssLicensesMenuActivity::class.java)
            startActivity(intent)
        }

        // about this app
        aboutButton.setOnClickListener {
            AboutDialog.show(this@InfoFragment)
        }
    }
}