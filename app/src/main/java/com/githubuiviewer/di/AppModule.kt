package com.githubuiviewer.di

import android.content.Context
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.*
import com.githubuiviewer.tools.sharedPrefsTools.SharedPref
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides //todo scope is not necessary for parameters stored within the module
    fun context(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideGitHubReposApi(retrofit: Retrofit): GitHubService {
        return retrofit.create(GitHubService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(HttpUrl.Builder().scheme(schema).host(host).build())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        )
    }
}