package com.githubuiviewer.ui.projectScreen.readme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.api.UnauthorizedException
import com.githubuiviewer.datasource.model.ReadMeModel
import com.githubuiviewer.tools.State
import com.githubuiviewer.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReadMeViewModelFragment @Inject constructor(
    private val gitHubService: GitHubService
) : BaseViewModel() {

    private val _readMeLiveData = MutableLiveData<State<ReadMeModel, Exception>>()
    val readMeLiveData
        get() = _readMeLiveData as LiveData<State<ReadMeModel, Exception>>

    fun getReadme(owner: String, repoName: String) {
        baseViewModelScope.launch {
            _readMeLiveData.value = State.Loading
            _readMeLiveData.postValue(State.Content(gitHubService.getReadme(owner, repoName)))
        }
    }

    override fun unauthorizedException() {
        super.unauthorizedException()
        _readMeLiveData.value = State.Error(UnauthorizedException())
    }

    //todo errors
}