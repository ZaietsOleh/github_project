package com.githubuiviewer.ui.mainActivity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.githubuiviewer.*
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.tools.navigator.Navigator
import com.githubuiviewer.ui.updateTokenFragment.UpdateTokenFragment

class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {

    val navigator by lazy {
        Navigator(supportFragmentManager, R.id.basic_fragment_holder)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        setupBasicFragment()
    }

    private fun setupBasicFragment() {
        navigator.showUserScreen(UserProfile.AuthorizedUser)
    }

    override fun onResume() {
        super.onResume()

        Log.d(MAIN_DEBUG_TAG, "onResume")
        getCodeFromUri(uri = intent.data)?.let {
            Log.d(MAIN_DEBUG_TAG, "getCodeFromUri has data")
            openUpdateFragmentUpdateToken(it)
        }
    }

    private fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(redirectUrl)) {
            return null
        }
        return uri.getQueryParameter("code")
    }

    private fun openUpdateFragmentUpdateToken(code: String) {
        Log.d(MAIN_DEBUG_TAG, "fun openUpdateFragmentDialog add UpdateTokenFragment")
        supportFragmentManager
            .beginTransaction()
            .add(UpdateTokenFragment.newInstance(code), "tag") //todo tag
            .commit()
    }
}