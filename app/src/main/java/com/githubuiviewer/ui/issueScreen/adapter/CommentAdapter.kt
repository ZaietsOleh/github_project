package com.githubuiviewer.ui.issueScreen.adapter

import android.provider.FontRequest
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.githubuiviewer.R
import com.githubuiviewer.datasource.model.IssueCommentRepos

class CommentAdapter(private var callback: ((IssueCommentRepos) -> Unit) = { }) : PagingDataAdapter<IssueCommentRepos, CommentHolder>(CommentComparator) {
    object CommentComparator : DiffUtil.ItemCallback<IssueCommentRepos>() {
        override fun areItemsTheSame(oldItem: IssueCommentRepos, newItem: IssueCommentRepos): Boolean {
            return oldItem.body == newItem.body //TODO check this diffCalback
        }

        override fun areContentsTheSame(oldItem: IssueCommentRepos, newItem: IssueCommentRepos): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.issue_comment_holder, parent, false)
        return CommentHolder(view, callback)
    }
}