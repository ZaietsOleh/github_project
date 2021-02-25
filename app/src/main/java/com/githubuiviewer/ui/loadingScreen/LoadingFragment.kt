package com.githubuiviewer.ui.loadingScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubuiviewer.R
import com.githubuiviewer.tools.navigator.BaseFragment

class LoadingFragment : BaseFragment(R.layout.issue_fragment) {
    companion object {
        fun newInstance() = LoadingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.loading_fragment, container, false)
    }
}