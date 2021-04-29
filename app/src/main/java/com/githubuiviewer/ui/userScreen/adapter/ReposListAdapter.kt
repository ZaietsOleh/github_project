package com.githubuiviewer.ui.userScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.githubuiviewer.R
import com.githubuiviewer.datasource.model.ReposResponse

class ReposListAdapter(
    private var callback: ((ReposResponse) -> Unit) = { }
) : ListAdapter<ReposResponse, ReposHolder>(SearchComparator2) {
    object SearchComparator2 : DiffUtil.ItemCallback<ReposResponse>() {
        override fun areItemsTheSame(oldItem: ReposResponse, newItem: ReposResponse): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ReposResponse, newItem: ReposResponse): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repos_holder, parent, false)
        return ReposHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ReposHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}