package com.githubuiviewer.ui.userScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.githubuiviewer.R
import com.githubuiviewer.datasource.model.ReposResponse

class ReposAdapter(private var callback: ((ReposResponse) -> Unit) = { }) :
    PagingDataAdapter<ReposResponse, ReposHolder>(SearchComparator) {
    object SearchComparator : DiffUtil.ItemCallback<ReposResponse>() {
        override fun areItemsTheSame(oldItem: ReposResponse, newItem: ReposResponse): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ReposResponse, newItem: ReposResponse): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ReposHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repos_holder, parent, false)

        return ReposHolder(view, callback)
    }
}