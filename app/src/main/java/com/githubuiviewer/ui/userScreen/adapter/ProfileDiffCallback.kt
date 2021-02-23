package com.githubuiviewer.ui.userScreen.adapter

import androidx.recyclerview.widget.DiffUtil

class ProfileDiffCallback: DiffUtil.ItemCallback<ProfileRecyclerState>() {
    override fun areItemsTheSame(oldItem: ProfileRecyclerState, newItem: ProfileRecyclerState): Boolean {
        return when {
            oldItem is ProfileRecyclerState.User && newItem is  ProfileRecyclerState.User -> oldItem.user.name == newItem.user.name
            oldItem is ProfileRecyclerState.Repos && newItem is  ProfileRecyclerState.Repos -> oldItem.repos.name == newItem.repos.name
            else -> oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItem: ProfileRecyclerState, newItem: ProfileRecyclerState): Boolean {
        return oldItem == newItem
    }
}