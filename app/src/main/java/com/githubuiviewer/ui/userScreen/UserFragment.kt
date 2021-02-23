package com.githubuiviewer.ui.userScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubuiviewer.App
import com.githubuiviewer.tools.MAIN_DEBUG_TAG
import com.githubuiviewer.R
import com.githubuiviewer.tools.USER_NOT_FOUND
import com.githubuiviewer.databinding.UserFragmentBinding
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.tools.navigator.BaseFragment
import com.githubuiviewer.ui.userScreen.adapter.ReposAdapter
import com.githubuiviewer.ui.userScreen.adapter.ReposRecyclerState
import javax.inject.Inject

class UserFragment(private val userProfile: UserProfile) : BaseFragment(R.layout.user_fragment) {
    companion object {
        fun newInstance(userProfile: UserProfile) = UserFragment(userProfile)
    }
    private val TAG = "UserFragment"

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
        viewModel.userInfoLiveData.observe(viewLifecycleOwner, Observer {
            updateUser(it)
        })
        viewModel.reposLiveData.observe(viewLifecycleOwner, Observer {
            updateRepos(it)
        })
    }

    private fun updateUser(state: State<UserResponse, String>) {
        when (state) {
            is State.Loading -> TODO("show loading" )
            is State.Error -> {
                if (state.error == USER_NOT_FOUND) {
                    binding.userGroup.setName(USER_NOT_FOUND)
                }
                else {
                    Log.d(MAIN_DEBUG_TAG, "UserFragment fun updateUser state error -> showLoginScreen")
                    navigation.showLoginScreen()
                }
            }
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
            is State.Error -> {
                binding.rvRepositories.adapter = ReposAdapter(ReposRecyclerState.Error(state.error)) {} //TODO(remove listener)
            }
            is State.Content -> {
                binding.rvRepositories.adapter = ReposAdapter(ReposRecyclerState.Content(state.data)) {
                    navigation.showProjectScreen()
                }
            }
        }
    }
}