package com.githubuiviewer.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.githubuiviewer.datasource.api.DataLoadingException
import com.githubuiviewer.datasource.api.NotFoundException
import com.githubuiviewer.datasource.api.UnauthorizedException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel: ViewModel() {
    private val TAG = "BaseViewModel"

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is UnauthorizedException -> unauthorizedException()
            is NotFoundException -> notFoundException()
            is DataLoadingException -> dataLoadingException()
        }
    }
    protected val baseViewModelScope = CoroutineScope(SupervisorJob() + exceptionHandler)

    protected open fun unauthorizedException() {
        Log.d(TAG, "unauthorizedException")
    }

    protected open fun notFoundException() {
        Log.d(TAG, "notFoundException")
    }

    protected open fun dataLoadingException() {
        Log.d(TAG, "dataLoadingException")
    }
}