package com.githubuiviewer.ui.projectScreen.contributors

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubuiviewer.R
import com.githubuiviewer.tools.USER_KEY
import com.githubuiviewer.ui.projectScreen.UserAndRepoName
import com.githubuiviewer.ui.projectScreen.readme.ReadMeFragment

class ContributorsFragment : Fragment() {

    private lateinit var userAndRepoName: UserAndRepoName
    private lateinit var viewModel: ContributorsViewModelFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userAndRepoName = it.getParcelable(USER_KEY) ?: UserAndRepoName("", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contributors_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContributorsViewModelFragment::class.java)
        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance(userAndRepoName: UserAndRepoName) =
            ContributorsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_KEY, userAndRepoName)
                }
            }
    }
}