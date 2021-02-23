package com.githubuiviewer.ui.mainActivity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.githubuiviewer.*
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.ui.Navigator
import com.githubuiviewer.ui.updateTokenFragment.UpdateTokenFragment

class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {

    val navigator by lazy {
        Navigator(supportFragmentManager, R.id.basic_fragment_holder)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        setupDi()
        setupBasicFragment()
    }

    private fun setupDi() {
        val app = application as App
        app.getComponent().inject(this)
    }

    private fun setupBasicFragment() {
        navigator.showUserScreen(UserProfile.AuthorizedUser)
    }

    override fun onResume() {
        super.onResume()

        getCodeFromUri(uri = intent.data)?.let {
            openUpdateFragmentDialog()
        }
    }

    private fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(redirectUrl)) {
            return null
        }
        return uri.getQueryParameter("code")
    }

    private fun openUpdateFragmentDialog() {
        supportFragmentManager
            .beginTransaction()
            .add(UpdateTokenFragment.newInstance(), "tag") //todo tag
            .commit()
    }
}