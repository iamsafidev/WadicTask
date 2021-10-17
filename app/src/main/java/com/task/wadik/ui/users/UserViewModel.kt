package com.task.wadik.ui.users

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.task.wadik.base.BaseViewModel
import com.task.wadik.ui.users.model.UserItem
import com.task.wadik.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class UserViewModel: BaseViewModel<UserViewModel.View>() {
    private lateinit var job: Job
    private var lifecycleOwner: LifecycleOwner? = null
    private val usersListLiveData = MutableLiveData<Resource<List<UserItem>>>()
    private val userRepository: UserRepository by KoinJavaComponent.inject(
        UserRepository::class.java
    )
    private val responseStatusObserver: Observer<in Resource<List<UserItem>>> = Observer { response ->
        when (response) {
            is Resource.Success -> {
                getView().onProgressDismiss()
                response.data?.let {
                    getView().onSuccess(it)
                }
            }
            is Resource.Error -> {
                getView().onProgressDismiss()
                response.message?.let {
                    getView().onError(it)
                }
            }
        }
    }

    fun addObserver(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    override fun attachView(view: View, lifecycleOwner: LifecycleOwner) {
        super.attachView(view, lifecycleOwner)
        usersListLiveData.observe(lifecycleOwner, responseStatusObserver)
    }

    override fun onCleared() {
        super.onCleared()
        usersListLiveData.removeObserver(responseStatusObserver)
    }

    fun getUsers() {
        getView().onProgress()
        job = viewModelScope.launch(Dispatchers.Default) {
            val ratesResponse =userRepository.getUsers()
            usersListLiveData.postValue(ratesResponse)
        }

    }

    fun cancelJob(){
        if(this::job.isInitialized)
            job.cancel()
    }

    interface View {
        fun onProgress()
        fun onSuccess(list: List<UserItem>)
        fun onProgressDismiss()
        fun onError(message: String)
    }
}

