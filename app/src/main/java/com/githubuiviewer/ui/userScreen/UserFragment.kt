package com.githubuiviewer.ui.userScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.databinding.UserFragmentBinding
import com.githubuiviewer.ui.BaseFragment
import javax.inject.Inject

class UserFragment : BaseFragment(R.layout.user_fragment) {
    companion object {
        fun newInstance() = UserFragment()
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

        //Там на какой-то клик переходим дальше
        //navigation.showLoginScreen()
    }

    private fun setupDi(){
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupLiveDataListeners(){

    }

}