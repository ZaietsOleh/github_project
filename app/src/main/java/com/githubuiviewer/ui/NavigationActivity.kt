package com.githubuiviewer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.githubuiviewer.R

class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {
    val navigator by lazy {
        Navigator(supportFragmentManager, R.id.basic_fragment_holder)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        navigator.showUserScreen()
    }
}