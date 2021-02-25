package com.githubuiviewer.tools.navigator

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.githubuiviewer.tools.MAIN_DEBUG_TAG
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.ui.issueScreen.IssueFragment
import com.githubuiviewer.ui.loadingScreen.LoadingFragment
import com.githubuiviewer.ui.loginScreen.LoginFragment
import com.githubuiviewer.ui.projectScreen.ProjectFragment
import com.githubuiviewer.ui.updateTokenFragment.UpdateTokenFragment
import com.githubuiviewer.ui.userScreen.UserFragment

class Navigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) {
    companion object {
        private const val USER_SCREEN_FRAGMENT = "USER_SCREEN_FRAGMENT"
        private const val LOGIN_SCREEN_FRAGMENT = "LOGIN_SCREEN_FRAGMENT"
        private const val PROJECT_SCREEN_FRAGMENT = "PROJECT_SCREEN_FRAGMENT"
        private const val ISSUE_SCREEN_FRAGMENT = "ISSUE_SCREEN_FRAGMENT"
        private const val LOADING_SCREEN_FRAGMENT = "LOADING_SCREEN_FRAGMENT"
    }

    fun showUserScreen(userProfile: UserProfile) {
        fragmentManager.beginTransaction()
            .replace(container, UserFragment.newInstance(userProfile))
            .commit()
    }

    fun showLoginScreen() {
        Log.d(MAIN_DEBUG_TAG, "NAVIGATOR start showLoginScreen")
        fragmentManager.beginTransaction()
            .add(container, LoginFragment.newInstance())
            .commit()
    }

    fun showFragmentUpdateToken(code: String) {
        fragmentManager
            .beginTransaction()
            .add(container, UpdateTokenFragment.newInstance(code)) //todo tag
            .commit()
    }

    fun showProjectScreen(userProfile: UserProfile, projectName: String) {
        fragmentManager.beginTransaction()
            .add(container, ProjectFragment.newInstance())
            .addToBackStack(PROJECT_SCREEN_FRAGMENT)
            .commit()
    }

    fun showIssueScreen() {
        fragmentManager.beginTransaction()
            .add(container, IssueFragment.newInstance())
            .addToBackStack(ISSUE_SCREEN_FRAGMENT)
            .commit()
    }

    fun showLoadingScreen() {
        fragmentManager.beginTransaction()
            .add(container, LoadingFragment.newInstance())
            .addToBackStack(LOADING_SCREEN_FRAGMENT)
            .commit()
    }

    fun closeLoadingScreen() {
        fragmentManager.popBackStack(LOADING_SCREEN_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}