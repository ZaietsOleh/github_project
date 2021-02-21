package com.githubuiviewer.di

import android.content.Context
import com.githubuiviewer.ui.MainActivity
import com.githubuiviewer.userScreen.UserFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [AppModule::class])
interface AppComponent {
    val context : Context

    fun inject(activity: MainActivity)

    fun inject(fragment: UserFragment)
}