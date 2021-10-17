package com.task.wadik.data.network

import com.task.wadik.ui.comments.model.CommentsItem
import com.task.wadik.ui.post.model.PostItem
import com.task.wadik.ui.users.model.UserItem
import com.task.wadik.utils.Resource
import retrofit2.Response

import retrofit2.http.GET

interface WebServices {
    @GET(Urls.USERS)
    suspend fun getUsers(): Response<List<UserItem>>

    @GET(Urls.POSTS)
    suspend fun getPosts(): Response<List<PostItem>>

    @GET(Urls.COMMENTS)
    suspend fun getComments(): Response<List<CommentsItem>>
}