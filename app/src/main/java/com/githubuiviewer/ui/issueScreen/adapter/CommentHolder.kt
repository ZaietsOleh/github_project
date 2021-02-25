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

    private fun toEmojiString(reactions: Reactions): String {
        val builder =  StringBuilder()
        if (reactions.confused > 0) {
            builder.append(Emoji.CONFUSED.emojiCode)
            builder.append(reactions.confused)
        }
        if (reactions.like > 0) {
            builder.append(Emoji.LIKE.emojiCode)
            builder.append(reactions.like)
        }
        if (reactions.dislike > 0) {
            builder.append(Emoji.DISLIKE.emojiCode)
            builder.append(reactions.dislike)
        }
        if (reactions.eyes > 0) {
            builder.append(Emoji.EYES.emojiCode)
            builder.append(reactions.eyes)
        }
        if (reactions.heart > 0) {
            builder.append(Emoji.HEART.emojiCode)
            builder.append(reactions.heart)
        }
        if (reactions.rocket > 0) {
            builder.append(Emoji.ROCKET.emojiCode)
            builder.append(reactions.rocket)
        }
        if (reactions.laugh > 0) {
            builder.append(Emoji.LAUGH.emojiCode)
            builder.append(reactions.laugh)
        }
        if (reactions.hooray > 0) {
            builder.append(Emoji.HOORAY.emojiCode)
            builder.append(reactions.hooray)
        }
        return builder.toString()
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
            etvEmoji.text = EmojiCompat.get().process(toEmojiString(comment.reactions))
            root.setOnClickListener {
                callback(comment)
            }
        }
    }
}