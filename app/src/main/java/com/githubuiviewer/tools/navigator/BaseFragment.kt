package com.githubuiviewer.tools.navigator

import androidx.fragment.app.Fragment
import com.githubuiviewer.ui.BaseViewModel
import com.githubuiviewer.ui.mainActivity.NavigationActivity
import com.githubuiviewer.ui.userScreen.UserFragmentViewModel
import javax.inject.Inject

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    protected val navigation: Navigator by lazy {
        (requireActivity() as NavigationActivity).navigator
    }

//    @Inject
//    lateinit var viewModel: BaseViewModel

//    fun showError() {
//        navigation.showLoginScreen()
//    }
}