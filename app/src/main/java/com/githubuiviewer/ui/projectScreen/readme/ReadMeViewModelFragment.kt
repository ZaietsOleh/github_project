package com.githubuiviewer.ui.projectScreen.readme

import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubuiviewer.data.repository.GitHubRepository
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.api.UnauthorizedException
import com.githubuiviewer.datasource.model.ReadMeModel
import com.githubuiviewer.tools.State
import com.githubuiviewer.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class   ReadMeViewModelFragment @Inject constructor(
    private val gitHubRepository: GitHubRepository
) : BaseViewModel() {

    private val _readMeLiveData = MutableLiveData<State<String, Exception>>()
    val readMeLiveData
        get() = _readMeLiveData as LiveData<State<String, Exception>>

    fun getReadme(owner: String, repoName: String) {
        baseViewModelScope.launch {
            _readMeLiveData.postValue(State.Loading)
            val readMe = gitHubRepository.getReadMe(owner, repoName)
            _readMeLiveData.postValue(State.Content(readMe))
        }
    }

    override fun unauthorizedException() {
        super.unauthorizedException()
        _readMeLiveData.value = State.Error(UnauthorizedException())
    }

    override fun dataLoadingException() {
        Log.d("TAG", "dataLoadingException")
    }
}