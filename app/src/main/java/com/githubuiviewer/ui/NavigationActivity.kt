package com.githubuiviewer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.githubuiviewer.R
import com.githubuiviewer.sharedPrefsTools.SharedPref

class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {

    val navigator by lazy {
        Navigator(supportFragmentManager, R.id.basic_fragment_holder)
    }

    private val sharedPreferences by lazy {
        SharedPref(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        navigator.showUserScreen()
    }

    override fun onResume() {
        super.onResume()

        //val response = githubUtils.getAccesToken(code)
//        val token = "${response.tokenType} ${response.accessToken}"
//        sharedPreferences.token = token
    }
}