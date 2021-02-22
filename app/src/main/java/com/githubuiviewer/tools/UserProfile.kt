package com.githubuiviewer.tools

sealed class UserProfile {
    object AuthorizedUser : UserProfile()
    data class PublicUser(val userNickname: String) : UserProfile()
}