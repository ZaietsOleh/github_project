package com.githubuiviewer.ui.userScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.databinding.UserFragmentBinding
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.SearchResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.INPUT_DELAY
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.tools.navigator.BaseFragment
import com.githubuiviewer.ui.userScreen.adapter.UserAdapter
import com.githubuiviewer.ui.userScreen.adapter.ReposAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserFragment(private val userProfile: UserProfile) : BaseFragment(R.layout.user_fragment),
    SearchView.OnQueryTextListener {
    companion object {
        fun newInstance(userProfile: UserProfile) = UserFragment(userProfile)
    }

    @Inject
    lateinit var viewModel: UserFragmentViewModel
    private lateinit var binding: UserFragmentBinding
    private var searchJob: Job? = null

    private val userAdapter = UserAdapter {
        navigation.showUserScreen(UserProfile.PublicUser(it.name))
    }

    private val reposAdapter = ReposAdapter {
        navigation.showProjectScreen(viewModel.userProfile, it.name)
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
        setupRecycler()
        setupSearch()

        viewModel.userProfile = userProfile
        viewModel.getContent()
    }

    private fun setupSearch() {
        binding.svSearchUser.apply {
            setOnSearchClickListener {
                binding.rvUsers.visibility = View.VISIBLE
            }

            setOnCloseListener {
                searchJob?.cancel()
                binding.rvUsers.visibility = View.GONE
                userAdapter.submitList(null)
                false
            }

            setOnQueryTextListener(this@UserFragment)
        }
    }

    private fun setupRecycler() {
        binding.apply {
            rvRepositories.adapter = reposAdapter
            rvRepositories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            rvUsers.adapter = userAdapter
            rvUsers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupLiveDataListeners() {
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

    private fun updateRepos(pagingData: PagingData<ReposResponse>) {
        viewModel.baseScope.launch {
            reposAdapter.submitData(pagingData)
        }
        navigation.closeLoadingScreen()
    }

    private fun updateSearch(searchResponse: SearchResponse?) {
        searchResponse?.let {
            userAdapter.submitList(it.items)
        }
    }

    private fun updateUser(state: State<UserResponse, Int>) {
        when (state) {
            is State.Loading -> {
                navigation.showLoadingScreen()
            }
            is State.Unauthorized -> navigation.showLoginScreen()
            is State.Error -> binding.userGroup.setName(getString(state.error))
            is State.Content -> {
                binding.userGroup.apply {
                    setImage(state.data.avatar_url)
                    setName(state.data.name)
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        newText?.let {
            searchJob = lifecycleScope.launch {
                delay(INPUT_DELAY)
                viewModel.getSearchable(newText)
            }
        }
        return false
    }
}