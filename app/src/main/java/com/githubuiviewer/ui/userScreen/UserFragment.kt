package com.githubuiviewer.ui.userScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.databinding.UserFragmentBinding
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.SearchResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.tools.navigator.BaseFragment
import com.githubuiviewer.ui.UserGroup
import com.githubuiviewer.ui.userScreen.adapter.ProfileAdapter
import com.githubuiviewer.ui.userScreen.adapter.ProfileRecyclerState
import javax.inject.Inject

class UserFragment(private val userProfile: UserProfile) : BaseFragment(R.layout.user_fragment), SearchView.OnQueryTextListener {
    companion object {
        fun newInstance(userProfile: UserProfile) = UserFragment(userProfile)
    }

    @Inject
    lateinit var viewModel: UserFragmentViewModel

    private lateinit var binding: UserFragmentBinding
    private val profileAdapter = ProfileAdapter() {
        when (it) {
            is UserGroup -> navigation.showUserScreen(UserProfile.PublicUser(it.getName()))
        }
    }

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
        setupRecyclerProfile()
        setupSearch()

        viewModel.getContent()
    }

    private fun setupSearch() {
        binding.svSearchUser.apply {
            setOnSearchClickListener {
                binding.apply {
                    userGroup.visibility = View.GONE
                }
                profileAdapter.submitList(null)
            }
            setOnCloseListener {
                binding.userGroup.visibility = View.VISIBLE
                viewModel.getContent()
                false
            }
            setOnQueryTextListener(this@UserFragment)
        }
    }

    private fun setupRecyclerProfile() {
        binding.rvRepositories.adapter = profileAdapter
        binding.rvRepositories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupLiveDataListeners() {
        viewModel.userProfile = userProfile
        viewModel.userInfoLiveData.observe(viewLifecycleOwner) {
            updateUser(it)
        }
        viewModel.reposLiveData.observe(viewLifecycleOwner) {
            updateRepos(it)
        }
        viewModel.searchLiveData.observe(viewLifecycleOwner) {
            updateSearch(it)
        }
    }

    private fun updateSearch(searchResponse: SearchResponse?) {
        searchResponse?.let {
            profileAdapter.submitList(searchResponse.items.map {
                ProfileRecyclerState.User(it)
            })
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { query ->
            viewModel.getSearchable(query)
        }
        return false
    }
}