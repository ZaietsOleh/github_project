package com.githubuiviewer.di

import android.content.Context
import com.githubuiviewer.tools.navigator.BaseFragment
import com.githubuiviewer.ui.mainActivity.NavigationActivity
import com.githubuiviewer.ui.updateTokenFragment.UpdateTokenFragment
import com.githubuiviewer.ui.userScreen.UserFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [AppModule::class])
interface AppComponent {
    val context : Context

    fun inject(fragment: UserFragment)

    fun inject(fragment: UpdateTokenFragment)

    fun inject(fragment: BaseFragment)

}