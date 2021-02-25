package com.githubuiviewer.ui.issueScreen

import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.databinding.EmojiChooserDialogBinding
import com.githubuiviewer.databinding.IssueFragmentBinding
import com.githubuiviewer.datasource.model.IssueCommentRepos
import com.githubuiviewer.tools.Emoji
import com.githubuiviewer.tools.navigator.BaseFragment
import com.githubuiviewer.ui.issueScreen.adapter.CommentAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class IssueFragment : BaseFragment(R.layout.issue_fragment) {
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
    private lateinit var binding: IssueFragmentBinding
    private val commentAdapter = CommentAdapter(::createReaction)

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
                    viewModel.createReaction(Emoji.LIKE, issueCommentRepos)
                    dialog.cancel()
                }
                dislike.setOnClickListener {
                    viewModel.createReaction(Emoji.DISLIKE, issueCommentRepos)
                    dialog.cancel()
                }
                hoorey.setOnClickListener {
                    viewModel.createReaction(Emoji.HOORAY, issueCommentRepos)
                    dialog.cancel()
                }
                rocket.setOnClickListener {
                    viewModel.createReaction(Emoji.ROCKET, issueCommentRepos)
                    dialog.cancel()
                }
                laugh.setOnClickListener {
                    viewModel.createReaction(Emoji.LAUGH, issueCommentRepos)
                    dialog.cancel()
                }
                eyes.setOnClickListener {
                    viewModel.createReaction(Emoji.EYES, issueCommentRepos)
                    dialog.cancel()
                }
                heart.setOnClickListener {
                    viewModel.createReaction(Emoji.HEART, issueCommentRepos)
                    dialog.cancel()
                }
                confused.setOnClickListener {
                    viewModel.createReaction(Emoji.CONFUSED, issueCommentRepos)
                    dialog.cancel()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = IssueFragmentBinding.inflate(inflater, container, false)
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
            rvIssueComments.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }
}