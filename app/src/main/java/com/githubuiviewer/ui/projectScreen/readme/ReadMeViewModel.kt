package com.githubuiviewer.ui.projectScreen.readme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubuiviewer.data.repository.GitHubRepository
import com.githubuiviewer.datasource.api.DataLoadingException
import com.githubuiviewer.datasource.api.NetworkException
import com.githubuiviewer.datasource.api.UnauthorizedException
import com.githubuiviewer.tools.State
import com.githubuiviewer.ui.BaseViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class ReadMeViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository
) : BaseViewModel() {

    private val _readMeLiveData = MutableLiveData<State<String, IOException>>()
    val readMeLiveData
        get() = _readMeLiveData as LiveData<State<String, IOException>>

    fun getReadme(owner: String, repoName: String) {
        baseViewModelScope.launch {
            _readMeLiveData.postValue(State.Loading)
            val readMe = gitHubRepository.getReadMe(owner, repoName)
            _readMeLiveData.postValue(State.Content(readMe))
        }
    }

    override fun unauthorizedException() {
        super.unauthorizedException()
        _readMeLiveData.postValue(State.Error(UnauthorizedException()))
    }

    override fun dataLoadingException() {
        super.dataLoadingException()
        _readMeLiveData.postValue(State.Error(DataLoadingException()))
    }

    override fun networkException() {
        super.networkException()
        _readMeLiveData.postValue(State.Error(NetworkException()))
    }
}