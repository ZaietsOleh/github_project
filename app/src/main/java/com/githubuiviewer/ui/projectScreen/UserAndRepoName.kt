package com.githubuiviewer.ui.projectScreen

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserAndRepoName(
    val userName: String,
    val repoName: String,
) : Parcelable