package com.githubuiviewer.tools

sealed class State<out DATA, out ERROR> {
    data class Content<DATA>(val data: DATA) : State<DATA, Nothing>()
    data class Error<ERROR>(val error: ERROR) : State<Nothing, ERROR>()
    object Unauthorized : State<Nothing, Nothing>()
    object Loading : State<Nothing, Nothing>()
}