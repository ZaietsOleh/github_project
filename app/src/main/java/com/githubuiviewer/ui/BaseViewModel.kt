package com.githubuiviewer.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.githubuiviewer.datasource.api.DataLoadingException
import com.githubuiviewer.datasource.api.NetworkException
import com.githubuiviewer.datasource.api.UnauthorizedException
import com.githubuiviewer.tools.BASE_VIEW_MODEL_TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class BaseViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is UnauthorizedException -> unauthorizedException()
            is NetworkException -> networkException()
            is DataLoadingException -> dataLoadingException()
        }
    }
    protected val baseViewModelScope = CoroutineScope(SupervisorJob() + exceptionHandler)

    protected open fun unauthorizedException() {
        Log.d(BASE_VIEW_MODEL_TAG, "unauthorizedException")
    }

    protected open fun networkException() {
        Log.d(BASE_VIEW_MODEL_TAG, "networkException")
    }

    protected open fun dataLoadingException() {
        Log.d(BASE_VIEW_MODEL_TAG, "dataLoadingException")
    }

    override fun onCleared() {
        baseViewModelScope.cancel()
        super.onCleared()
    }
}