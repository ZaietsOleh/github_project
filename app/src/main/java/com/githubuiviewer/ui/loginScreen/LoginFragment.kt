package com.githubuiviewer.ui.loginScreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.githubuiviewer.databinding.LoginFragmentBinding
import com.githubuiviewer.tools.*

//todo just fragment???
class LoginFragment : Fragment() {

    private lateinit var binding : LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(MAIN_DEBUG_TAG, "onViewCreated login dialog fragment")
        //isCancelable = false ??TODO THINK ABOUT THIS
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