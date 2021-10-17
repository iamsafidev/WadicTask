package com.task.wadik.ui.users.adapters

import com.task.wadik.BR
import com.task.wadik.R
import com.task.wadik.adapters.base.BaseRecyclerViewAdapter
import com.task.wadik.databinding.ItemUsersBinding
import com.task.wadik.ui.users.model.UserItem


class UserAdapter: BaseRecyclerViewAdapter<UserItem, ItemUsersBinding>() {
    override fun getLayout() = R.layout.item_users
    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemUsersBinding>,
        position: Int
    ) {
        val users = items[position]
        holder.binding.setVariable(BR.users, users)
        holder.binding.executePendingBindings()
    }
}