package xyz.godi.budgetmanager.info.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_info.*
import xyz.godi.budgetmanager.core.livedata.EventObserver

import xyz.godi.budgetmanager.info.R
import javax.inject.Inject

class InfoFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: InfoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
                .get(InfoViewModel::class.java)
        setupViewModel()
        setupButtons()
    }

    private fun setupViewModel() {
        viewModel.navigateReselect.observe(viewLifecycleOwner, EventObserver{
            infoScrollView.smoothScrollTo(0,0)
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
    }
}