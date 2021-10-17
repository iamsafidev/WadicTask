package com.task.wadik.di

import com.task.wadik.ui.comments.CommentsRepository
import com.task.wadik.ui.post.PostRepository
import com.task.wadik.ui.users.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { UserRepository(apiInterface = get()) }
    factory { PostRepository(apiInterface = get()) }
    factory { CommentsRepository(apiInterface = get()) }
}