package com.githubuiviewer.ui.userScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.databinding.UserFragmentBinding
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.ui.BaseFragment
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

        viewModel.userProfile = userProfile
        viewModel.getContent()
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
            is State.Error -> navigation.showLoginScreen()
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
            is State.Error -> Log.d(TAG, "update repos error")
            is State.Content -> {

            }
        }
    }

}