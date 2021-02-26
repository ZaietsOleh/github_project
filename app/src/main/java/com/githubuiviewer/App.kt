package com.githubuiviewer

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import com.githubuiviewer.di.AppComponent
import com.githubuiviewer.di.AppModule
import com.githubuiviewer.di.DaggerAppComponent

class App : Application() {

    lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val config = BundledEmojiCompatConfig(this)
        EmojiCompat.init(config)

        daggerComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getComponent(): AppComponent {
        return daggerComponent
    }
}