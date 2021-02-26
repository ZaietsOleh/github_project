package com.githubuiviewer.ui.updateTokenFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.databinding.UpadateTokenFragmentBinding
import com.githubuiviewer.tools.FragmentArgsDelegate
import com.githubuiviewer.tools.UpdatingState
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.ui.issueScreen.IssueFragment
import com.githubuiviewer.ui.issueScreen.IssuesDetailsParameter
import com.githubuiviewer.ui.navigator.BaseFragment
import javax.inject.Inject

class UpdateTokenFragment : BaseFragment(R.layout.upadate_token_fragment) {

    override var parentContainer: ConstraintLayout? = null

    private lateinit var binding: UpadateTokenFragmentBinding
    private val refreshKey by FragmentArgsDelegate<String>(REFRESH_KEY)

    @Inject
    lateinit var viewModel: UpdateTokenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UpadateTokenFragmentBinding.inflate(inflater, container, false)
        parentContainer = binding.container
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupListeners()
        setupLivaDataListener()
        viewModel.updateToken(refreshKey)
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupLivaDataListener() {
        viewModel.updateStatusLiveData.observe(viewLifecycleOwner, {
            it?.let { updatingState ->
                when (updatingState) {
                    UpdatingState.LOADING -> showProgressBar()
                    UpdatingState.COMPLETED -> openUserFragment()
                    UpdatingState.ERROR -> showError()
                }
            }
        })
    }

    private fun setupListeners() {
        binding.btnTryAgain.setOnClickListener {
            viewModel.updateToken(refreshKey)
        }
    }

    private fun showProgressBar() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            tvUpdatingData.visibility = View.VISIBLE
            tvError.visibility = View.GONE
            btnTryAgain.visibility = View.GONE
        }
    }

    private fun openUserFragment() {
        navigation.showMainUserProfile(UserProfile.AuthorizedUser)
    }

    private fun showError() {
        binding.apply {
            progressBar.visibility = View.GONE
            tvUpdatingData.visibility = View.GONE
            tvError.visibility = View.VISIBLE
            btnTryAgain.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val REFRESH_KEY = "REFRESH_KEY"

        fun newInstance(code: String) = UpdateTokenFragment().apply {
            arguments = Bundle().apply {
                putString(REFRESH_KEY, code)
            }
        }
    }
}