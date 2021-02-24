package com.githubuiviewer.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.githubuiviewer.R
import com.githubuiviewer.datasource.api.DataLoadingException
import com.githubuiviewer.datasource.api.NotFoundException
import com.githubuiviewer.datasource.api.UnauthorizedException
import com.githubuiviewer.tools.State
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel: ViewModel() {
    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is UnauthorizedException -> unauthorizedException()
            is NotFoundException -> Log.d("TAGF", "NotFoundException")
            is DataLoadingException -> Log.d("TAGF", "DataLoadingException")
        }
    }

    protected val baseViewModelScope = CoroutineScope(   SupervisorJob() + exceptionHandler)

    protected open fun unauthorizedException() {
        Log.d("TAGF", "DataLoadingException")
    }
}