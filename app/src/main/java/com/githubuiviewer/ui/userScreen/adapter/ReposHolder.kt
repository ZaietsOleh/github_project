package com.githubuiviewer.ui.userScreen.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.githubuiviewer.databinding.ReposHolderBinding
import com.githubuiviewer.datasource.model.ReposResponse


class ReposHolder(
    view: View, private var callback: ((ReposResponse) -> Unit) = { }
) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ReposHolderBinding

    fun onBind(reposResponse: ReposResponse) {
        binding = ReposHolderBinding.bind(itemView)
        binding.apply {
            tvReposName.setOnClickListener {
                callback(reposResponse)
            }
            tvReposName.text = reposResponse.name
        }
    }
}