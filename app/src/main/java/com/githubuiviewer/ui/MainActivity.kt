package com.githubuiviewer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.githubuiviewer.App
import com.githubuiviewer.R
import com.githubuiviewer.sharedPrefsTools.SharedPref
import com.githubuiviewer.userScreen.UserFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var model: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = this.application as App
        app.getComponent().inject(this)

        //todo model.checkAuthorized()

        setupLiveDataListener()
        setupBasicFragment()
    }

    private fun setupLiveDataListener(){
    }

    private fun setupBasicFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.basic_fragment_holder, UserFragment.newInstance())
            .commit()
    }
}