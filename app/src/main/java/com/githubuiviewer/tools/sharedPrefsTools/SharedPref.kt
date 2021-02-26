package com.githubuiviewer.tools.sharedPrefsTools

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.githubuiviewer.tools.PRIVATE_SHARED_PREF
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(context: Context) {

  private companion object {
    const val KEY_TOKEN = "KEY_TOKEN"
  }

  private val sharedPreferences: SharedPreferences by lazy {
    context.getSharedPreferences(PRIVATE_SHARED_PREF  , MODE_PRIVATE)
  }

  var token: String by SharedPrefDelegate(sharedPreferences, KEY_TOKEN, "")
}