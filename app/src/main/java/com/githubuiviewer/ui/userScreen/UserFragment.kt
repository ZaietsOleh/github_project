package com.githubuiviewer.ui.userScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubuiviewer.App
import com.githubuiviewer.tools.MAIN_DEBUG_TAG
import com.githubuiviewer.R
import com.githubuiviewer.databinding.UserFragmentBinding
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.ui.BaseFragment
import com.githubuiviewer.ui.userScreen.adapter.ProfileAdapter
import com.githubuiviewer.ui.userScreen.adapter.ProfileRecyclerState
import javax.inject.Inject

class UserFragment(private val userProfile: UserProfile) : BaseFragment(R.layout.user_fragment) {
    companion object {
        fun newInstance(userProfile: UserProfile) = UserFragment(userProfile)
    }

    @Inject
    lateinit var viewModel: UserFragmentViewModel

    private lateinit var binding: UserFragmentBinding
    private val profileAdapter = ProfileAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvRepositories.adapter = profileAdapter

        setupDi()
        setupLiveDataListeners()
        setupRecyclerRepos()
        setupSearch()

        viewModel.userProfile = userProfile
        viewModel.getContent()
    }

    private fun setupSearch() {
        binding.svSearchUser.setOnSearchClickListener {

        }
        binding.svSearchUser.setOnCloseListener {
            Log.d("TRATRA", "On SERCK2")
            false
        }
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
            is State.Loading -> {

            }
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
            is State.Loading -> {

            }
            is State.Unauthorized -> navigation.showLoginScreen()
            is State.Error -> {
                profileAdapter.submitList(listOf(ProfileRecyclerState.Error(state.error)))
            }
            is State.Content -> {
                profileAdapter.submitList(
                    state.data.map {
                        ProfileRecyclerState.Repos(it)
                    }
                )
            }
        }
    }
}