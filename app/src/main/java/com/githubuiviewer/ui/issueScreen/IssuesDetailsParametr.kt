package com.githubuiviewer.ui.issueScreen

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IssuesDetailsParameter(
    val owner: String,
    val repo: String,
    val issue_number: Int
) : Parcelable
