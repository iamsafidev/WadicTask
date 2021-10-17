package com.task.wadik.ui.users

import com.task.wadik.App
import com.task.wadik.R
import com.task.wadik.data.network.WebServices
import com.task.wadik.ui.users.model.UserItem
import com.task.wadik.utils.Resource

class UserRepository(private val apiInterface: WebServices) {
    suspend fun getUsers(): Resource<List<UserItem>> {
        return try {
            val response = apiInterface.getUsers()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else
                Resource.Error(response.message())
        } catch (e: Exception) {
            Resource.Error(e.message ?: App.instance?.let { it.getString(R.string.default_error_message) }?:"")
        }
    }
}