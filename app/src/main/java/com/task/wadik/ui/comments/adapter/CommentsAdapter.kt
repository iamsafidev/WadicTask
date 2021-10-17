package com.task.wadik.ui.comments.adapter

import android.util.Log
import com.task.wadik.BR
import com.task.wadik.R
import com.task.wadik.adapters.base.BaseRecyclerViewAdapter
import com.task.wadik.databinding.ItemCommentsBinding
import com.task.wadik.ui.comments.model.CommentsItem

class CommentsAdapter : BaseRecyclerViewAdapter<CommentsItem, ItemCommentsBinding>() {
    override fun getLayout() = R.layout.item_comments
    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemCommentsBinding>,
        position: Int
    ) {
        val comments = items[position]
        holder.binding.setVariable(BR.comments, comments)
        holder.binding.executePendingBindings()
    }
}