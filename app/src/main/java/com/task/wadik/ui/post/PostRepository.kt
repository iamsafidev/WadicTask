package com.task.wadik.ui.post

import com.task.wadik.App
import com.task.wadik.R
import com.task.wadik.data.network.WebServices
import com.task.wadik.ui.post.model.PostItem
import com.task.wadik.utils.Resource

class PostRepository(private val apiInterface: WebServices) {
    suspend fun getPosts(): Resource<List<PostItem>> {
        return try {
            val response = apiInterface.getPosts()
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: App.instance?.let { it.getString(R.string.default_error_message) }?:"")
        }
    }
}