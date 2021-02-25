package com.githubuiviewer.ui.projectScreen

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.tools.navigator.BaseFragment
import com.githubuiviewer.ui.projectScreen.contributors.ContributorsFragment
import com.githubuiviewer.ui.projectScreen.issues.IssuesFragment
import com.githubuiviewer.ui.projectScreen.readme.ReadMeFragment

class RepoAdapter(fragment: BaseFragment, val userAndRepoName: UserAndRepoName) : FragmentStateAdapter(fragment){

    override fun getItemCount() = Page.values().size

    override fun createFragment(position: Int): Fragment {
        return when (Page.values()[position]) {
            Page.README -> {
                //todo without new instance
                ReadMeFragment.newInstance(userAndRepoName)
            }
            Page.CONTRIBUTORS -> {
                //todo without new instance
                ContributorsFragment.newInstance(userAndRepoName)
            }
            Page.ISSUES -> {
                //todo without new instance
                IssuesFragment.newInstance(userAndRepoName)
            }
        }
    }

    @StringRes
    fun getItemTabName(position: Int): Int {
        return Page.values()[position].titlePage
    }
}

