package com.task.wadik.ui.post

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.task.wadik.base.BaseViewModel
import com.task.wadik.ui.post.model.PostItem
import com.task.wadik.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class PostViewModel : BaseViewModel<PostViewModel.View>() {
    private lateinit var job: Job
    private var lifecycleOwner: LifecycleOwner? = null
    private val commentsListLiveData = MutableLiveData<Resource<List<PostItem>>>()
    private val postRepository: PostRepository by KoinJavaComponent.inject(
        PostRepository::class.java
    )
    private val responseStatusObserver: Observer<in Resource<List<PostItem>>> =
        Observer { response ->
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

    override fun attachView(view: PostViewModel.View, lifecycleOwner: LifecycleOwner) {
        super.attachView(view, lifecycleOwner)
        commentsListLiveData.observe(lifecycleOwner, responseStatusObserver)
    }

    override fun onCleared() {
        super.onCleared()
        commentsListLiveData.removeObserver(responseStatusObserver)
    }

    fun getPosts() {
        getView().onProgress()
        job = viewModelScope.launch(Dispatchers.Default) {
            val ratesResponse = postRepository.getPosts()
            commentsListLiveData.postValue(ratesResponse)
        }

    }

    fun cancelJob() {
        if (this::job.isInitialized)
            job.cancel()
    }

    interface View {
        fun onProgress()
        fun onSuccess(list: List<PostItem>)
        fun onProgressDismiss()
        fun onError(message: String)
    }
}