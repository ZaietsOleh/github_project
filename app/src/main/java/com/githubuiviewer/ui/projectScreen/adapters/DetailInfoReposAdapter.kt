package com.githubuiviewer.ui.projectScreen.adapters

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.githubuiviewer.tools.navigator.BaseFragment
import com.githubuiviewer.ui.projectScreen.Page
import com.githubuiviewer.ui.projectScreen.UserAndRepoName
import com.githubuiviewer.ui.projectScreen.contributors.ContributorsFragment
import com.githubuiviewer.ui.projectScreen.issues.IssuesFragment
import com.githubuiviewer.ui.projectScreen.readme.ReadMeFragment

class DetailInfoReposAdapter(
    fragment: BaseFragment,
    private val userAndRepoName: UserAndRepoName
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = Page.values().size

    override fun createFragment(position: Int): Fragment {
        return when (Page.values()[position]) {
            Page.README -> {
                ReadMeFragment.newInstance(userAndRepoName)
            }
            Page.CONTRIBUTORS -> {
                ContributorsFragment.newInstance(userAndRepoName)
            }
            Page.ISSUES -> {
                IssuesFragment.newInstance(userAndRepoName)
            }
        }
    }

    @StringRes
    fun getItemTabName(position: Int): Int {
        return Page.values()[position].titlePage
    }
}

