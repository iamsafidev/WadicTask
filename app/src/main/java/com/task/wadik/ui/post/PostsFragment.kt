package com.task.wadik.ui.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.wadik.MainActivity
import com.task.wadik.R
import com.task.wadik.databinding.FragmentCommentsBinding
import com.task.wadik.databinding.FragmentPostsBinding
import com.task.wadik.ui.post.adapter.PostAdapter
import com.task.wadik.ui.post.model.PostItem


class PostsFragment : Fragment(), PostViewModel.View {
    private lateinit var mainActivity: MainActivity
    private val adapter: PostAdapter by lazy {
        PostAdapter()
    }
    private var isViewShown: Boolean = false
    private val postViewModel: PostViewModel by viewModels()
    private lateinit var postFragmentBinding: FragmentPostsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        postFragmentBinding = FragmentPostsBinding.inflate(inflater, container, false)
        return postFragmentBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        postViewModel.apply {
            attachView(this@PostsFragment, this@PostsFragment)
            addObserver(this@PostsFragment)
        }
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        postFragmentBinding.recyclerView.layoutManager = linearLayoutManager
        postFragmentBinding.recyclerView.adapter = adapter

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (view != null && menuVisible) {
            isViewShown = true
            if (mainActivity.getInternetConnectionStatus()) {
                postFragmentBinding.errorMessage.visibility = View.GONE
                postViewModel.getPosts()
            } else {
                postFragmentBinding.errorMessage.visibility = View.VISIBLE
            }

        } else {
            isViewShown = false
            postViewModel.cancelJob()
        }
    }


    override fun onProgress() {
        postFragmentBinding.progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(list: List<PostItem>) {
        adapter.items = list
    }

    override fun onProgressDismiss() {
        postFragmentBinding.progressBar.visibility = View.GONE
    }

    override fun onError(message: String) {
        postFragmentBinding.errorMessage.visibility = View.VISIBLE
        postFragmentBinding.errorMessage.text = message
    }
}