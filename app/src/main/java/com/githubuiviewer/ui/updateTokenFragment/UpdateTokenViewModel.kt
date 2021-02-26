package com.githubuiviewer.ui.updateTokenFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubuiviewer.tools.MAIN_DEBUG_TAG
import com.githubuiviewer.data.repository.GitHubRepository
import com.githubuiviewer.tools.UpdatingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateTokenViewModel @Inject constructor(
    private val logInRepository: GitHubRepository
) : ViewModel() {

    private val _updateStatusLiveData = MutableLiveData<UpdatingState>()
    val updateStatusLiveData: LiveData<UpdatingState> = _updateStatusLiveData

    fun updateToken(code: String) {
        _updateStatusLiveData.postValue(UpdatingState.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                logInRepository.updateToken(code)
                Log.d(
                    MAIN_DEBUG_TAG,
                    "updateTokenVIEWMODEL -> update token-> put success in live data"
                )
                _updateStatusLiveData.postValue(UpdatingState.COMPLETED)
            } catch (e: Exception) {
                Log.d(MAIN_DEBUG_TAG, "error update token ${e.message}")
                _updateStatusLiveData.postValue(UpdatingState.ERROR)
            }
        }
    }
}