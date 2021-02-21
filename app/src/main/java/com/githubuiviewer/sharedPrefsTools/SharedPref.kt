package com.githubuiviewer.sharedPrefsTools

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(context: Context) {

  private companion object {
    const val KEY_TOKEN = "KEY_TOKEN"
  }

  //todo test is it ok???
  private val sharedPreferences: SharedPreferences by lazy {
    context.getSharedPreferences("test", MODE_PRIVATE)
  }

  //todo add inject for this deligate???
  var token: String by SharedPrefDelegate(sharedPreferences, KEY_TOKEN, "")

}