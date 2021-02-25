package com.githubuiviewer.ui.issueScreen.adapter

import android.view.View
import androidx.emoji.text.EmojiCompat
import androidx.recyclerview.widget.RecyclerView
import com.githubuiviewer.databinding.IssueCommentHolderBinding
import com.githubuiviewer.datasource.model.IssueCommentRepos
import com.githubuiviewer.datasource.model.Reactions
import com.githubuiviewer.tools.Emoji
import java.lang.StringBuilder

class CommentHolder(view: View, private val callback: (IssueCommentRepos) -> Unit = {}) :
    RecyclerView.ViewHolder(view) {
    private lateinit var binding: IssueCommentHolderBinding

    private fun collectReaction(reactions: Reactions): String {
        return StringBuilder().apply {
            if (reactions.confused > 0) {
                append(Emoji.CONFUSED.emojiCode)
                append(reactions.confused)
            }
            if (reactions.like > 0) {
                append(Emoji.LIKE.emojiCode)
                append(reactions.like)
            }
            if (reactions.dislike > 0) {
                append(Emoji.DISLIKE.emojiCode)
                append(reactions.dislike)
            }
            if (reactions.eyes > 0) {
                append(Emoji.EYES.emojiCode)
                append(reactions.eyes)
            }
            if (reactions.heart > 0) {
                append(Emoji.HEART.emojiCode)
                append(reactions.heart)
            }
            if (reactions.rocket > 0) {
                append(Emoji.ROCKET.emojiCode)
                append(reactions.rocket)
            }
            if (reactions.laugh > 0) {
                append(Emoji.LAUGH.emojiCode)
                append(reactions.laugh)
            }
            if (reactions.hooray > 0) {
                append(Emoji.HOORAY.emojiCode)
                append(reactions.hooray)
            }
        }.toString()
    }

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
            etvEmoji.text = EmojiCompat.get().process(collectReaction(comment.reactions))
            root.setOnClickListener {
                callback(comment)
            }
        }
    }
}