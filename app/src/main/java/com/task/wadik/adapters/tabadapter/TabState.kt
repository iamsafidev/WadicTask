package com.task.wadik.adapters.tabadapter

import androidx.fragment.app.Fragment
import com.task.wadik.ui.comments.CommentsFragment
import com.task.wadik.ui.post.PostsFragment
import com.task.wadik.ui.users.UsersFragment

enum class TabState(val id: Int, val tabName: String, val fragment: Fragment) {
    USERS(1, "Users", UsersFragment()),
    POSTS(0, "POSTS", PostsFragment()),
    COMMENTS(2, "COMMENTS", CommentsFragment())
}