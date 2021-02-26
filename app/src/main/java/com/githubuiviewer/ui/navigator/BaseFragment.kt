package com.githubuiviewer.ui.navigator

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.githubuiviewer.R
import com.githubuiviewer.ui.mainActivity.NavigationActivity

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    protected val navigation: Navigator by lazy {
        (requireActivity() as NavigationActivity).navigator
    }

    protected fun showError(@StringRes error: Int) {
        MaterialDialog(requireContext()).show {
            title(R.string.error_title)
            message(error)
        }
    }

    fun showLoading() {
        navigation.showLoadingScreen()
    }
    fun closeLoading() {
        navigation.closeLoadingScreen()
    }
}