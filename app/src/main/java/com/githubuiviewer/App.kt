package com.githubuiviewer

import android.app.Application
import com.githubuiviewer.di.AppComponent
import com.githubuiviewer.di.AppModule
import com.githubuiviewer.di.DaggerAppComponent

class App : Application() {

    lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        daggerComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getComponent(): AppComponent {
        return daggerComponent
    }
}