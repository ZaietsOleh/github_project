package com.githubuiviewer.ui.userScreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.githubuiviewer.ERROR_COUNT
import com.githubuiviewer.R
import com.githubuiviewer.datasource.model.ReposResponse
import java.lang.Error

class ReposAdapter(
    private val repositories: ReposRecyclerState,
    private val onClickListener: View.OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (repositories) {
            is ReposRecyclerState.Error -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.error_holder, parent, false)
                ErrorHolder(view)
            }
            is ReposRecyclerState.Content -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.repos_holder, parent, false)
                ReposHolder(view, onClickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ErrorHolder -> holder.onBind((repositories as ReposRecyclerState.Error).errorText)
            is ReposHolder -> holder.onBind((repositories as ReposRecyclerState.Content).data[position])
        }
    }

    override fun getItemCount(): Int {
        return when (repositories) {
            is ReposRecyclerState.Error -> ERROR_COUNT
            is ReposRecyclerState.Content -> repositories.data.size
        }
    }
}