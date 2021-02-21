package com.githubuiviewer.datasource.api

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when (response.code()) {
            401 -> throw UnauthorizedException("Unauthorized")
            404 -> throw NotFoundException("Resource not found")
            in 400..500 -> throw DataLoadingException("Something went wrong")
        }

        val bodyString = response.body()!!.string()
        return response.newBuilder()
            .body(ResponseBody.create(response.body()?.contentType(), bodyString))
            .build()
    }
}