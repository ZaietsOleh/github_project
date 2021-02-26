package com.githubuiviewer.ui.userScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.databinding.UserFragmentBinding
import com.githubuiviewer.datasource.api.DataLoadingException
import com.githubuiviewer.datasource.api.NetworkException
import com.githubuiviewer.datasource.api.UnauthorizedException
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.*
import com.githubuiviewer.ui.navigator.BaseFragment
import com.githubuiviewer.ui.projectScreen.UserAndRepoName
import com.githubuiviewer.ui.userScreen.adapter.UserAdapter
import com.githubuiviewer.ui.userScreen.adapter.ReposAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class UserFragment() : BaseFragment(R.layout.user_fragment) {
    companion object {
        fun newInstance(userProfile: UserProfile) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_KEY, userProfile)
                }
            }
    }

    @Inject
    lateinit var viewModel: UserFragmentViewModel
    private lateinit var binding: UserFragmentBinding
    private var searchJob: Job? = null
    private var userProfile by FragmentArgsDelegate<UserProfile>(USER_KEY)

    private val userAdapter = UserAdapter(::onUserClick)

    private val reposAdapter = ReposAdapter(::onReposClick)

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

    private fun onUserClick(userResponse: UserResponse) {
        navigation.showUserScreen(UserProfile.PublicUser(userResponse.name))
    }

    private fun onReposClick(reposResponse: ReposResponse) {
        navigation.showProjectScreen(
            UserAndRepoName(
                viewModel.currentUserName,
                reposResponse.name
            )
        )
    }

    private fun setupSearch() {
        binding.svSearchUser.apply {
            setOnSearchClickListener {
                binding.rvUsers.visibility = View.VISIBLE
            }

            setOnCloseListener {
                searchJob?.cancel()
                binding.rvUsers.visibility = View.GONE
                hideKeyboard()
                false
            }

            setOnQueryTextListener(SearchListener { query ->
                searchJob?.cancel()
                query?.let {
                    searchJob = lifecycleScope.launch {
                        delay(INPUT_DELAY)
                        viewModel.getSearchable(query)
                    }
                }
            })
        }
    }

    private fun setupRecycler() {
        setupRepositoriesAdapter()
        setupUsersAdapter()
    }

    private fun setupRepositoriesAdapter() {
        binding.apply {
            rvRepositories.adapter = reposAdapter
            rvRepositories.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupUsersAdapter() {
        binding.apply {
            rvUsers.adapter = userAdapter
            rvUsers.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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
    }

    private fun updateSearch(pagingData: PagingData<UserResponse>) {
        viewModel.baseScope.launch {
            userAdapter.submitData(pagingData)
        }
    }

    private fun updateUser(state: State<UserResponse, Exception>) {
        when (state) {
            is State.Loading -> {
                showLoading()
            }
            is State.Error -> {
                closeLoading()
                when (state.error) {
                    is UnauthorizedException -> navigation.showLoginScreen()
                    is DataLoadingException -> showError(R.string.dataloading_error)
                    is NetworkException -> showError(R.string.netwotk_error)
                }
            }
            is State.Content -> {
                closeLoading()
                binding.userGroup.apply {
                    setImage(state.data.avatar_url)
                    setName(state.data.name)
                }
            }
        }
    }
}