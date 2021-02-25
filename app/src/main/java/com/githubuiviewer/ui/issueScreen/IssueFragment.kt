package com.githubuiviewer.ui.issueScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.databinding.IssueDetailFragmentBinding
import com.githubuiviewer.datasource.model.IssueCommentRepos
import com.githubuiviewer.tools.navigator.BaseFragment
import com.githubuiviewer.ui.issueScreen.adapter.CommentAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class IssueFragment : BaseFragment(R.layout.issue_detail_fragment) {
    companion object {
        private const val OWNER = "OWNER"
        private const val REPO = "REPO"
        private const val ISSUE_NUMBER = "ISSUE_NUMBER"

        fun newInstance(owner: String, repo: String, issue_number: Int) = IssueFragment().apply {
            arguments = Bundle().apply {
                putString(OWNER, owner)
                putString(REPO, repo)
                putInt(ISSUE_NUMBER, issue_number)
            }
        }
    }

    @Inject
    lateinit var viewModel: IssueViewModel
    private lateinit var binding: IssueDetailFragmentBinding
    private val commentAdapter = CommentAdapter {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = IssueDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupAdapter()
        setupObserver()

        viewModel.getContent()
    }

    private fun setupObserver() {
        viewModel.commentLiveData.observe(viewLifecycleOwner) {
            updateComments(it)
        }
    }

    private fun updateComments(pagingData: PagingData<IssueCommentRepos>) {
        viewModel.baseScope.launch {
            commentAdapter.submitData(pagingData)
        }
    }


    private fun setupAdapter() {
        binding.apply {
            rvIssueComments.adapter = commentAdapter
            rvIssueComments.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }
}