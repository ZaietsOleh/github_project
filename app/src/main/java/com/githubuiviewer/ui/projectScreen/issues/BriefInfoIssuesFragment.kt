package com.githubuiviewer.ui.projectScreen.issues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.databinding.IssuesFragmentBinding
import com.githubuiviewer.datasource.model.IssueResponse
import com.githubuiviewer.tools.FragmentArgsDelegate
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.USER_KEY
import com.githubuiviewer.ui.navigator.BaseFragment
import com.githubuiviewer.ui.issueScreen.IssuesDetailsParameter
import com.githubuiviewer.ui.projectScreen.UserAndRepoName
import com.githubuiviewer.ui.projectScreen.adapters.IssuesAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class BriefInfoIssuesFragment : BaseFragment(R.layout.issues_fragment) {

    override val parentContainer: ConstraintLayout
        get() = binding.root

    @Inject
    lateinit var viewModel: IssuesBriefInfoViewModel

    private lateinit var binding: IssuesFragmentBinding
    private val userAndRepoName by FragmentArgsDelegate<UserAndRepoName>(USER_KEY)

    private val issuesAdapter = IssuesAdapter(::onIssueClick)

    private fun onIssueClick(issueResponse: IssueResponse) {
        navigation.showDetailIssueScreen(IssuesDetailsParameter(
            owner = userAndRepoName.userName,
            repo = userAndRepoName.repoName,
            issue_number = issueResponse.number
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = IssuesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupDi()
        setupRecyclerView()
        viewModel.getIssues(userAndRepoName)
        setupLiveDataListener()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupRecyclerView(){
        binding.apply {
            rvIssues.adapter = issuesAdapter
            rvIssues.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupLiveDataListener() {
        viewModel.issuesLiveData.observe(viewLifecycleOwner){
            when(it){
                is State.Loading -> {}
                is State.Content -> updateIssuesRecyclerView(it.data)
                is State.Error -> {}
            }
        }
    }

    private fun updateIssuesRecyclerView(pagingData: PagingData<IssueResponse>){
        viewModel.viewModelScope.launch {
            issuesAdapter.submitData(pagingData)
        }
    }

    companion object {
        fun newInstance(userAndRepoName: UserAndRepoName) =
            BriefInfoIssuesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_KEY, userAndRepoName)
                }
            }
    }

}