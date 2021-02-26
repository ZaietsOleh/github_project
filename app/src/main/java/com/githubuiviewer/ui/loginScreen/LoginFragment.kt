package com.githubuiviewer.ui.loginScreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.githubuiviewer.R
import com.githubuiviewer.databinding.LoginFragmentBinding
import com.githubuiviewer.tools.*
import com.githubuiviewer.ui.navigator.BaseFragment

class LoginFragment : BaseFragment(R.layout.login_fragment) {

    private lateinit var binding : LoginFragmentBinding
    override var parentContainer: ConstraintLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        parentContainer = binding.container
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }
    
    private fun setupListeners() {
        binding.btnSignIn.setOnClickListener {
            startGitHubLogin()
        }
    }

    private fun startGitHubLogin() {
        val authIntent = Intent(Intent.ACTION_VIEW, buildAuthGitHubUrl())
        startActivity(authIntent)
    }

    private fun buildAuthGitHubUrl(): Uri {
        return Uri.Builder()
            .scheme(schema)
            .authority(logInHost)
            .appendEncodedPath("login/oauth/authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("scope", scopes)
            .appendQueryParameter("redirect_url", redirectUrl)
            .build()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}