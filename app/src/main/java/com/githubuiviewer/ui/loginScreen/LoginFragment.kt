package com.githubuiviewer.ui.loginScreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.githubuiviewer.*
import com.githubuiviewer.databinding.LoginFragmentBinding
import com.githubuiviewer.ui.BaseFragment

class LoginFragment : BaseFragment(R.layout.login_fragment) {

    private lateinit var binding : LoginFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LoginFragmentBinding.bind(view)
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
            .authority(com.githubuiviewer.host)
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