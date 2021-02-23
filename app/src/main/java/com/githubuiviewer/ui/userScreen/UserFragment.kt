package com.githubuiviewer.ui.userScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.DATA_NOT_FOUND
import com.githubuiviewer.databinding.UserFragmentBinding
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.ui.BaseFragment
import com.githubuiviewer.ui.userScreen.adapter.ReposAdapter
import com.githubuiviewer.ui.userScreen.adapter.ReposRecyclerState
import javax.inject.Inject

class UserFragment(private val userProfile: UserProfile) : BaseFragment(R.layout.user_fragment) {
    companion object {
        fun newInstance(userProfile: UserProfile) = UserFragment(userProfile)
    }

    @Inject
    lateinit var viewModel: UserFragmentViewModel

    private lateinit var binding: UserFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupLiveDataListeners()
        setupRecyclerRepos()

        viewModel.userProfile = userProfile
        viewModel.getContent()
    }

    private fun setupRecyclerRepos() {
        binding.rvRepositories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun setupDi(){
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupLiveDataListeners(){
        viewModel.userInfoLiveData.observe(viewLifecycleOwner) {
            updateUser(it)
        }
        viewModel.reposLiveData.observe(viewLifecycleOwner) {
            updateRepos(it)
        }
    }

    private fun updateUser(state: State<UserResponse, String>) {
        when (state) {
            is State.Loading -> TODO("show loading" )
            is State.Unauthorized -> navigation.showLoginScreen()
            is State.Error -> binding.userGroup.setName(state.error)
            is State.Content -> {
                binding.userGroup.apply {
                    setImage(state.data.avatar_url)
                    setName(state.data.name)
                }
            }
        }
    }

    private fun updateRepos(state: State<List<ReposResponse>, String>) {
        when (state) {
            is State.Loading -> TODO("show loading" )
            is State.Unauthorized -> navigation.showLoginScreen()
            is State.Error -> {
                binding.rvRepositories.adapter = ReposAdapter(ReposRecyclerState.Error(state.error))
            }
            is State.Content -> {
                binding.rvRepositories.adapter = ReposAdapter(ReposRecyclerState.Content(state.data)) {
                    navigation.showProjectScreen()
                }
            }
        }
    }
}