package com.githubuiviewer.ui.updateTokenFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubuiviewer.App
import com.githubuiviewer.MAIN_DEBUG_TAG
import com.githubuiviewer.R
import com.githubuiviewer.databinding.UpadateTokenFragmentBinding
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.tools.navigator.Navigator
import javax.inject.Inject

class UpdateTokenFragment(private val code: String) : Fragment() {

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
        setupLivaDataListener()
        viewModel.updateToken(code)
    }

    private fun setupDi(){
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupLivaDataListener(){
        viewModel.updateStatusLiveData.observe(viewLifecycleOwner, {
            when(it){
                is State.Loading -> showProgressBar()
                is State.Content -> openUserFragment()
                is State.Error -> showError()
            }
        })
    }

    private fun showProgressBar(){
        //todo
    }

    private fun openUserFragment(){
        //navigator.showUserScreen(UserProfile.AuthorizedUser)
    }

    private fun showError(){
        Log.d(MAIN_DEBUG_TAG, "UpdateToken Fragment show error. Error get token")
        binding.progressBar.setBackgroundColor(resources.getColor(R.color.design_default_color_error))
    }

    companion object {
        fun newInstance(code : String) = UpdateTokenFragment(code)
    }
}