package com.githubuiviewer.tools

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class UserProfile : Parcelable {
    @Parcelize
    object AuthorizedUser : UserProfile(), Parcelable
    @Parcelize
    data class PublicUser(val userNickname: String) : UserProfile(), Parcelable
}