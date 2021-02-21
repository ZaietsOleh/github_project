package com.githubuiviewer.di

import android.content.Context
import com.githubuiviewer.ui.NavigationActivity
import com.githubuiviewer.ui.userScreen.UserFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [AppModule::class])
interface AppComponent {
    val context : Context

    fun inject(activity: NavigationActivity)

    fun inject(fragment: UserFragment)
}