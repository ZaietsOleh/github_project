package com.githubuiviewer.ui.userScreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.githubuiviewer.ERROR_COUNT
import com.githubuiviewer.R

class ProfileAdapter(
    private val onClickListener: View.OnClickListener? = null
) : ListAdapter<ProfileRecyclerState, RecyclerView.ViewHolder>(ProfileDiffCallback()) {
    enum class ProfileType {
        ERROR,
        USER,
        REPOS
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProfileRecyclerState.Error -> ProfileType.ERROR
            is ProfileRecyclerState.Repos -> ProfileType.REPOS
            is ProfileRecyclerState.User -> ProfileType.USER
        }.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ProfileType.values()[viewType]) {
            ProfileType.ERROR -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.error_holder, parent, false)
                ErrorHolder(view)
            }
            ProfileType.USER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_holder, parent, false)
                UserHolder(view, onClickListener)
            }
            ProfileType.REPOS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.repos_holder, parent, false)
                ReposHolder(view, onClickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ErrorHolder -> holder.onBind((getItem(position) as ProfileRecyclerState.Error).errorText)
            is ReposHolder -> holder.onBind((getItem(position) as ProfileRecyclerState.Repos).repos)
            is UserHolder -> holder.onBind((getItem(position) as ProfileRecyclerState.User).user)
        }
    }
}