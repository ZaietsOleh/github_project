package com.githubuiviewer.ui.projectScreen

import androidx.annotation.StringRes
import com.githubuiviewer.R

enum class Page(@StringRes val titlePage: Int) {
    README(R.string.page_readme_title),
    CONTRIBUTORS(R.string.page_contributors_title),
    ISSUES(R.string.page_issues_title)
}