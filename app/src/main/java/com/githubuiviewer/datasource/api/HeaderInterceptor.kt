package com.githubuiviewer.datasource.api

import com.githubuiviewer.tools.sharedPrefsTools.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val sharedPref: SharedPref
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Accept", "application/vnd.github.squirrel-girl-preview+json")
                .addHeader("Authorization", sharedPref.token)
                .build()
        )
    }
}