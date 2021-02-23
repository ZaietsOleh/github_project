package com.githubuiviewer.ui.updateTokenFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubuiviewer.App
import com.githubuiviewer.tools.MAIN_DEBUG_TAG
import com.githubuiviewer.R
import com.githubuiviewer.databinding.UpadateTokenFragmentBinding
import com.githubuiviewer.tools.UpdatingState
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.tools.navigator.BaseFragment
import com.githubuiviewer.tools.navigator.Navigator
import javax.inject.Inject

class UpdateTokenFragment(private val code: String) :
    BaseFragment(R.layout.upadate_token_fragment) {

    private val navigator by lazy {
        Navigator(requireActivity().supportFragmentManager, R.id.basic_fragment_holder)
    }

    private lateinit var binding: UpadateTokenFragmentBinding

    @Inject
    lateinit var viewModel: UpdateTokenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UpadateTokenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupListeners()
        setupLivaDataListener()
        viewModel.updateToken(code)
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

    private fun setupListeners(){
        binding.btnTryAgain.setOnClickListener {
            viewModel.updateToken(code)
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
        navigator.showUserScreen(UserProfile.AuthorizedUser)
    }

    private fun showError() {
        Log.d(MAIN_DEBUG_TAG, "UpdateToken Fragment show error. Error get token")
        binding.apply {
            progressBar.visibility = View.GONE
            tvUpdatingData.visibility = View.GONE
            tvError.visibility = View.VISIBLE
            btnTryAgain.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance(code: String) = UpdateTokenFragment(code)
    }
}