package com.githubuiviewer.ui.mainActivity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.githubuiviewer.*
import com.githubuiviewer.tools.MAIN_DEBUG_TAG
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.ui.navigator.Navigator
import com.githubuiviewer.tools.redirectUrl

class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {

    val navigator by lazy {
        Navigator(supportFragmentManager, R.id.basic_fragment_holder)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(MAIN_DEBUG_TAG, "onCreate main actvity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        getCodeFromUri(uri = intent.data)?.let {
            Log.d(MAIN_DEBUG_TAG, "getCodeFromUri has data")
            openFragmentUpdateToken(it)
            return
        }

        setupBasicFragment()
    }

    private fun setupBasicFragment() {
        navigator.showMainUserProfile(UserProfile.AuthorizedUser)
    }

    private fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(redirectUrl)) {
            return null
        }
        return uri.getQueryParameter("code")
    }

    private fun openFragmentUpdateToken(code: String) {
        Log.d(MAIN_DEBUG_TAG, "start from activity UpdateTokenFragment")
        navigator.showFragmentUpdateToken(code)
    }
}