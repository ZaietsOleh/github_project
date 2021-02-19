package com.githubuiviewer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.sharedPrefsTools.SharedPref
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences : SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = this.application as App
        app.getComponent().inject(this)

        checkAuthorized()
    }

    private fun checkAuthorized() {
        if (sharedPreferences.token.isEmpty()) {
            //todo start dialog log in
        }
        else{
            //todo set basic user fragment
        }
    }

}