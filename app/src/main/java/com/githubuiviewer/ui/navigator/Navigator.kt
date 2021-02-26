package com.githubuiviewer.ui.navigator

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.ui.issueScreen.IssueFragment
import com.githubuiviewer.ui.issueScreen.IssuesDetailsParameter
import com.githubuiviewer.ui.loginScreen.LoginFragment
import com.githubuiviewer.ui.projectScreen.ParenRepoProjectFragment
import com.githubuiviewer.ui.projectScreen.UserAndRepoName
import com.githubuiviewer.ui.updateTokenFragment.UpdateTokenFragment
import com.githubuiviewer.ui.userScreen.UserFragment

class Navigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) {
    companion object {
        private const val USER_SCREEN_FRAGMENT = "USER_SCREEN_FRAGMENT"
        private const val PROJECT_SCREEN_FRAGMENT = "PROJECT_SCREEN_FRAGMENT"
        private const val ISSUE_SCREEN_FRAGMENT = "ISSUE_SCREEN_FRAGMENT"
        private const val LOADING_SCREEN_FRAGMENT = "LOADING_SCREEN_FRAGMENT"
    }

    fun showMainUserProfile(userProfile: UserProfile){
        fragmentManager.beginTransaction()
            .replace(container, UserFragment.newInstance(userProfile))
            .commit()
    }

    fun showUserScreen(userProfile: UserProfile) {
        fragmentManager.beginTransaction()
            .replace(container, UserFragment.newInstance(userProfile))
            .addToBackStack(USER_SCREEN_FRAGMENT)
            .commit()
    }

    fun showLoginScreen() {
        fragmentManager.beginTransaction()
            .add(container, LoginFragment.newInstance())
            .commit()
    }

    fun showFragmentUpdateToken(code: String) {
        fragmentManager
            .beginTransaction()
            .add(container, UpdateTokenFragment.newInstance(code))
            .commit()
    }

    fun showProjectScreen(userAndRepoName: UserAndRepoName) {
        fragmentManager.beginTransaction()
            .add(container, ParenRepoProjectFragment.newInstance(userAndRepoName))
            .addToBackStack(PROJECT_SCREEN_FRAGMENT)
            .commit()
    }

    fun showDetailIssueScreen(issuesDetailsParameter: IssuesDetailsParameter) {
        fragmentManager.beginTransaction()
            .add(container, IssueFragment.newInstance(issuesDetailsParameter))
            .addToBackStack(ISSUE_SCREEN_FRAGMENT)
            .commit()
    }
}