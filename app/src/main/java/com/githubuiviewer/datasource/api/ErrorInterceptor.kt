package com.githubuiviewer.datasource.api

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject


class ErrorInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!isConnected()) throw NetworkException()
        val response = chain.proceed(request)



        when (response.code) {
            401 -> throw UnauthorizedException()
            in 400..500 -> throw DataLoadingException()
        }

        Log.d("TAG", response.message)
        Log.d("TAG", response.networkResponse!!.message)

        val bodyString = response.body?.string() ?: "No info from response body(null)"
        return response.newBuilder()
            .body(bodyString.toResponseBody(response.body?.contentType()))
            .build()
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}