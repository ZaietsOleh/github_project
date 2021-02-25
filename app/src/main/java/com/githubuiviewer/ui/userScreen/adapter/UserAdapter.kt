package com.githubuiviewer.ui.userScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.githubuiviewer.R
import com.githubuiviewer.datasource.model.UserResponse

class UserAdapter(
    private var callback: ((UserResponse) -> Unit) = { }
) : PagingDataAdapter<UserResponse, UserHolder>(UserDiffCallback) {

    object UserDiffCallback : DiffUtil.ItemCallback<UserResponse>() {
        override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_holder, parent, false)
        return UserHolder(view, callback)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }
}