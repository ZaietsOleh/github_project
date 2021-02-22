package com.githubuiviewer.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.githubuiviewer.R
import com.githubuiviewer.databinding.UserGroupBinding

class UserGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding =
        UserGroupBinding.bind(inflate(context, R.layout.user_group, this))

    fun setName(name: String) {
        binding.tvUserName.text = name
    }

    fun setImage(imageUrl: String) {
        Glide.with(this).load(imageUrl).into(binding.ivUserAvatar)
    }
}
