package com.githubuiviewer.ui.userScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.githubuiviewer.R
import com.githubuiviewer.datasource.model.ReposResponse

class ReposAdapter(private val repositories: List<ReposResponse>): RecyclerView.Adapter<ReposHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repos_holder, parent, false)

        return ReposHolder(view)
    }

    override fun onBindViewHolder(holder: ReposHolder, position: Int) {
        holder.onBind(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size

}