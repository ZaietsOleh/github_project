package com.githubuiviewer.ui.userScreen.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.githubuiviewer.databinding.ErrorHolderBinding

class ErrorHolder(view: View) : RecyclerView.ViewHolder(view) {
    private lateinit var binding: ErrorHolderBinding

    fun onBind(errorText: String) {
        binding = ErrorHolderBinding.bind(itemView)
        binding.apply {
            tvErrorText.text = errorText
        }
    }
}