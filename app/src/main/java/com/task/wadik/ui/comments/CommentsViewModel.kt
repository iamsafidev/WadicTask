package com.task.wadik.ui.comments


import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.task.wadik.base.BaseViewModel
import com.task.wadik.ui.comments.model.CommentsItem
import com.task.wadik.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class CommentsViewModel : BaseViewModel<CommentsViewModel.View>() {
    private lateinit var job: Job
    private var lifecycleOwner: LifecycleOwner? = null
    private val commentsListLiveData = MutableLiveData<Resource<List<CommentsItem>>>()
    private val commentsRepository: CommentsRepository by KoinJavaComponent.inject(
        CommentsRepository::class.java
    )
    private val responseStatusObserver: Observer<in Resource<List<CommentsItem>>> = Observer { response ->
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
        commentsListLiveData.observe(lifecycleOwner, responseStatusObserver)
    }

    override fun onCleared() {
        super.onCleared()
         commentsListLiveData.removeObserver(responseStatusObserver)
    }

    fun getComments() {
        getView().onProgress()
        job = viewModelScope.launch(Dispatchers.Default) {
            val ratesResponse =commentsRepository.getComments()
            commentsListLiveData.postValue(ratesResponse)
        }

    }

    fun cancelJob(){
        if(this::job.isInitialized)
            job.cancel()
    }

    interface View {
        fun onProgress()
        fun onSuccess(list: List<CommentsItem>)
        fun onProgressDismiss()
        fun onError(message: String)
    }
}

