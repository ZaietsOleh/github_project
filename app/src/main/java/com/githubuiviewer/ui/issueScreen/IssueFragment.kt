package com.githubuiviewer.ui.issueScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.databinding.EmojiChooserDialogBinding
import com.githubuiviewer.datasource.api.DataLoadingException
import com.githubuiviewer.datasource.api.NetworkException
import com.githubuiviewer.datasource.api.UnauthorizedException
import com.githubuiviewer.databinding.IssueDetailFragmentBinding
import com.githubuiviewer.datasource.model.IssueCommentRepos
import com.githubuiviewer.tools.Emoji
import com.githubuiviewer.tools.FragmentArgsDelegate
import com.githubuiviewer.tools.State
import com.githubuiviewer.ui.navigator.BaseFragment
import com.githubuiviewer.ui.issueScreen.adapter.CommentAdapter
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class IssueFragment : BaseFragment(R.layout.issue_detail_fragment) {

    @Inject
    lateinit var viewModel: IssueViewModel
    private lateinit var binding: IssueDetailFragmentBinding
    private val commentAdapter = CommentAdapter(::createReaction)
    private val issuesDetailsParameter by FragmentArgsDelegate<IssuesDetailsParameter>(ISSUE_KEY)

    private fun createReaction(issueCommentRepos: IssueCommentRepos) {
        val emojiBinding = EmojiChooserDialogBinding.inflate(layoutInflater)
        val dialog = MaterialDialog(requireContext())
        setupEmojiListener(emojiBinding, dialog, issueCommentRepos)
        dialog.customView(view = emojiBinding.root)
        dialog.show()
    }

    private fun setupEmojiListener(
        emojiBinding: EmojiChooserDialogBinding,
        dialog: MaterialDialog,
        issueCommentRepos: IssueCommentRepos
    ) {
        emojiBinding.apply {
            viewModel.apply {
                like.setOnClickListener {
                    createReaction(Emoji.LIKE, issueCommentRepos)
                    dialog.cancel()
                }
                dislike.setOnClickListener {
                    createReaction(Emoji.DISLIKE, issueCommentRepos)
                    dialog.cancel()
                }
                hoorey.setOnClickListener {
                    createReaction(Emoji.HOORAY, issueCommentRepos)
                    dialog.cancel()
                }
                rocket.setOnClickListener {
                    createReaction(Emoji.ROCKET, issueCommentRepos)
                    dialog.cancel()
                }
                laugh.setOnClickListener {
                    createReaction(Emoji.LAUGH, issueCommentRepos)
                    dialog.cancel()
                }
                eyes.setOnClickListener {
                    createReaction(Emoji.EYES, issueCommentRepos)
                    dialog.cancel()
                }
                heart.setOnClickListener {
                    createReaction(Emoji.HEART, issueCommentRepos)
                    dialog.cancel()
                }
                confused.setOnClickListener {
                    createReaction(Emoji.CONFUSED, issueCommentRepos)
                    dialog.cancel()
                }
            }
        }
    }

    override val parentContainer: ConstraintLayout
        get() = binding.root

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
        
        viewModel.issuesDetailsParameter = issuesDetailsParameter
        viewModel.getContent()
    }

    private fun setupObserver() {
        viewModel.commentLiveData.observe(viewLifecycleOwner) {
            updateComments(it)
        }
    }

    private fun updateComments(state: State<PagingData<IssueCommentRepos>, IOException>) {
        when (state) {
            is State.Loading -> showLoading()
            is State.Error -> {
                closeLoading()
                when (state.error) {
                    is UnauthorizedException -> navigation.showLoginScreen()
                    is DataLoadingException -> showError(R.string.dataloading_error)
                    is NetworkException -> showError(R.string.netwotk_error)
                }
            }
            is State.Content -> {
                closeLoading()
                viewModel.baseScope.launch {
                    commentAdapter.submitData(state.data)
                }
            }
        }
    }

    private fun setupAdapter() {
        binding.apply {
            rvIssueComments.adapter = commentAdapter
            rvIssueComments.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    companion object {
        private const val ISSUE_KEY = "ISSUE_KEY"

        fun newInstance(issuesDetailsParameter: IssuesDetailsParameter) = IssueFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ISSUE_KEY, issuesDetailsParameter)
            }
        }
    }
}