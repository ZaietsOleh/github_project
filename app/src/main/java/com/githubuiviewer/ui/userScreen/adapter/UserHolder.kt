package com.githubuiviewer.ui.userScreen.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.githubuiviewer.databinding.UserHolderBinding
import com.githubuiviewer.datasource.model.UserResponse

class UserHolder(view: View, private val onClickListener: View.OnClickListener?) : RecyclerView.ViewHolder(view) {
    private lateinit var binding: UserHolderBinding

    fun onBind(userResponse: UserResponse) {
        binding = UserHolderBinding.bind(itemView)
        binding.ugUser.apply {
            setName(userResponse.name)
            setImage(userResponse.avatar_url)
        }
    }
}