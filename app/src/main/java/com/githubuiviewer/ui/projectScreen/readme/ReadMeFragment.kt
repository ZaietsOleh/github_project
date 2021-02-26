package com.githubuiviewer.ui.projectScreen.readme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.databinding.ReadMeFragmentBinding
import com.githubuiviewer.datasource.api.UnauthorizedException
import com.githubuiviewer.tools.FragmentArgsDelegate
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.USER_KEY
import com.githubuiviewer.ui.navigator.BaseFragment
import com.githubuiviewer.ui.projectScreen.UserAndRepoName
import java.lang.Exception
import javax.inject.Inject

class ReadMeFragment : BaseFragment(R.layout.read_me_fragment) {

    @Inject
    lateinit var viewModel: ReadMeViewModelFragment

    private lateinit var binding: ReadMeFragmentBinding
    private var userAndRepoName by FragmentArgsDelegate<UserAndRepoName>(USER_KEY)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ReadMeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        viewModel.getReadme(userAndRepoName.userName, userAndRepoName.repoName)
        setupLiveDataListener()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupLiveDataListener() {
        viewModel.readMeLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Content -> showContent(it.data)
                is State.Error -> showError(it.error)
                is State.Loading -> showProgressBar()
            }
        }
    }

    private fun showContent(readMeModel: String) {
        binding.tvReadMe.text = readMeModel
    }

    private fun showError(error: Exception) {
        when (error) {
            is UnauthorizedException -> {
                navigation.showLoginScreen()
            }
            else -> showProgressBar()
        }
    }

    private fun showProgressBar() {

    }

    companion object {
        fun newInstance(userAndRepoName: UserAndRepoName) =
            ReadMeFragment().apply {
                this.userAndRepoName = userAndRepoName
            }
    }
}

