package com.githubuiviewer.ui

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.githubuiviewer.ui.issueScreen.IssueFragment
import com.githubuiviewer.ui.loginScreen.LoginFragment
import com.githubuiviewer.ui.projectScreen.ProjectFragment
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
    }

    fun showUserScreen() {
        fragmentManager.beginTransaction()
            .add(container, UserFragment.newInstance())
            .addToBackStack(USER_SCREEN_FRAGMENT)
            .commit()
    }

    fun showLoginScreen() {
        fragmentManager.beginTransaction()
            .add(container, LoginFragment.newInstance())
            .addToBackStack(LOGIN_SCREEN_FRAGMENT)
            .commit()
    }

    fun showProjectScreen() {
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
}