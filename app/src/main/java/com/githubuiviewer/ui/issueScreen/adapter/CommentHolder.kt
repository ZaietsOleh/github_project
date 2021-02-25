package com.githubuiviewer.ui.issueScreen.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.githubuiviewer.databinding.IssueCommentHolderBinding
import com.githubuiviewer.datasource.model.IssueCommentRepos
import kotlinx.coroutines.flow.callbackFlow

class CommentHolder(view: View, private val callback: (IssueCommentRepos) -> Unit = {}) :RecyclerView.ViewHolder(view) {
    private lateinit var binding: IssueCommentHolderBinding

    fun onBind(comment: IssueCommentRepos) {
        binding = IssueCommentHolderBinding.bind(itemView)

        binding.apply {
            ugUser.setName(comment.user.name)
            ugUser.setImage(comment.user.avatar_url)
            tvBody.text = comment.body
            tvTime.text = comment.created_at
            root.setOnClickListener {
                callback(comment)
            }
        }
    }
}