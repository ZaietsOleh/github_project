package com.githubuiviewer.ui.projectScreen.issues

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubuiviewer.R
import com.githubuiviewer.tools.USER_KEY
import com.githubuiviewer.tools.navigator.BaseFragment
import com.githubuiviewer.ui.projectScreen.UserAndRepoName
import com.githubuiviewer.ui.projectScreen.readme.ReadMeFragment

class IssuesFragment : BaseFragment(R.layout.issues_fragment) {

    private lateinit var userAndRepoName: UserAndRepoName
    private lateinit var viewModel: IssuesViewModelFragment

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
        return inflater.inflate(R.layout.issues_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IssuesViewModelFragment::class.java)
        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance(userAndRepoName: UserAndRepoName) =
            IssuesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_KEY, userAndRepoName)
                }
            }
    }

}