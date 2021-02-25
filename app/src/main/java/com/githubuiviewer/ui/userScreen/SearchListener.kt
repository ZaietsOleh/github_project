package com.githubuiviewer.ui.userScreen

import android.widget.SearchView

class SearchListener(
    private val callback: (String?) -> Unit
) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        callback(newText)
        return false
    }
}