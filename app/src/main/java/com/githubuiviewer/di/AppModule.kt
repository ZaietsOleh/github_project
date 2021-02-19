package com.githubuiviewer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides //todo scope is not necessary for parameters stored within the module
    fun context(): Context {
        return context
    }

}