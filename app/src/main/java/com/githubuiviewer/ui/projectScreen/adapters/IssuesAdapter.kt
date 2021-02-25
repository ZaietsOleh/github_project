package com.githubuiviewer.ui.projectScreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.githubuiviewer.R
import com.githubuiviewer.datasource.model.IssueResponse

class IssuesAdapter(
    private var callback: ((IssueResponse) -> Unit) = { }
) : PagingDataAdapter<IssueResponse, IssueHolder>(SearchComparator) {

    object SearchComparator : DiffUtil.ItemCallback<IssueResponse>() {
        override fun areItemsTheSame(oldItem: IssueResponse, newItem: IssueResponse) =
            oldItem.number == newItem.number

        override fun areContentsTheSame(oldItem: IssueResponse, newItem: IssueResponse) =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: IssueHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.issue_comment_holder, parent, false)
        return IssueHolder(view, callback)
    }

}