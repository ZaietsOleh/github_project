package com.githubuiviewer.ui.loadingScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.githubuiviewer.R
import com.githubuiviewer.ui.navigator.BaseFragment

class LoadingFragment : Fragment(R.layout.issue_detail_fragment) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.loading_fragment, container, false)
    }

    companion object {
        fun newInstance() = LoadingFragment()
    }
}