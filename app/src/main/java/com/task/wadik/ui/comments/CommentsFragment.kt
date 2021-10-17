package com.task.wadik.ui.comments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.wadik.MainActivity
import com.task.wadik.databinding.FragmentCommentsBinding
import com.task.wadik.ui.comments.adapter.CommentsAdapter
import com.task.wadik.ui.comments.model.CommentsItem
import org.w3c.dom.Comment

class CommentsFragment : Fragment(), CommentsViewModel.View {
    private lateinit var mainActivity: MainActivity
    private val adapter: CommentsAdapter by lazy {
        CommentsAdapter()
    }

    private var isViewShown: Boolean = false
    private val commentsViewModel: CommentsViewModel by viewModels()
    private lateinit var commentsFragmentBinding: FragmentCommentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        commentsFragmentBinding = FragmentCommentsBinding.inflate(inflater, container, false)
        return commentsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        commentsViewModel.apply {
            attachView(this@CommentsFragment, this@CommentsFragment)
            addObserver(this@CommentsFragment)
        }
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        commentsFragmentBinding.recyclerView.layoutManager = linearLayoutManager
        commentsFragmentBinding.recyclerView.adapter = adapter

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (view != null && menuVisible) {
            isViewShown = true
            if (mainActivity.getInternetConnectionStatus()) {
                commentsFragmentBinding.errorMessage.visibility = View.GONE
                commentsViewModel.getComments()
            } else {
                commentsFragmentBinding.errorMessage.visibility = View.VISIBLE
            }

        } else {
            isViewShown = false
            commentsViewModel.cancelJob()
        }
    }


    override fun onProgress() {
        commentsFragmentBinding.progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(list: List<CommentsItem>) {
        adapter.items = list
    }

    override fun onProgressDismiss() {
        commentsFragmentBinding.progressBar.visibility = View.GONE
    }

    override fun onError(message: String) {
        commentsFragmentBinding.errorMessage.visibility = View.VISIBLE
        commentsFragmentBinding.errorMessage.text = message
    }
}
