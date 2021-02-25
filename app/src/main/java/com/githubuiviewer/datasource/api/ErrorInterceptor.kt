package com.githubuiviewer.datasource.api

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when (response.code) {
            401 -> throw UnauthorizedException("Unauthorized")
            404 -> throw NotFoundException("Resource not found")
            in 400..500 -> throw DataLoadingException("Something went wrong")
        }

        val bodyString = response.body?.string() ?: "No info from response body(null)"
        return response.newBuilder()
            .body(bodyString.toResponseBody(response.body?.contentType()))
            .build()
    }
}