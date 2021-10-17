package com.task.wadik.ui.post.adapter

import com.task.wadik.BR
import com.task.wadik.R
import com.task.wadik.adapters.base.BaseRecyclerViewAdapter
import com.task.wadik.databinding.ItemCommentsBinding
import com.task.wadik.databinding.ItemPostBinding
import com.task.wadik.ui.post.model.PostItem

class PostAdapter: BaseRecyclerViewAdapter<PostItem, ItemPostBinding>() {
    override fun getLayout() = R.layout.item_post
    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemPostBinding>,
        position: Int
    ) {
        val post = items[position]
        holder.binding.setVariable(BR.post, post)
        holder.binding.executePendingBindings()
    }
}