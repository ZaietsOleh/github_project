package com.githubuiviewer.ui.updateTokenFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubuiviewer.MAIN_DEBUG_TAG
import com.githubuiviewer.data.repository.LogInRepository
import com.githubuiviewer.tools.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateTokenViewModel @Inject constructor(
    private val logInRepository: LogInRepository
): ViewModel() {

    //todo not string in state
    private val _updateStatusLiveData = MutableLiveData<State<Boolean, String>>().apply {
        value = State.Loading
    }
    val updateStatusLiveData: LiveData<State<Boolean, String>> = _updateStatusLiveData

    fun updateToken(code: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                logInRepository.updateToken(code)
                withContext(Dispatchers.Main) {
                    Log.d(MAIN_DEBUG_TAG, "updateTokenVIEWMODEL -> update token-> put succes in live data")
                    _updateStatusLiveData.value = State.Content(true)
                }
            } catch (e : Exception){
                Log.d(MAIN_DEBUG_TAG, "errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr")
                withContext(Dispatchers.Main) {
                    _updateStatusLiveData.value = State.Error("error update")
                }
            }
        }
    }
}