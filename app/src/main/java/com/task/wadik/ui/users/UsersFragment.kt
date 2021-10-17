package com.task.wadik.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.wadik.MainActivity
import com.task.wadik.databinding.FragmentUsersBinding
import com.task.wadik.ui.users.adapters.UserAdapter
import com.task.wadik.ui.users.model.UserItem


class UsersFragment : Fragment(),UserViewModel.View{
    private lateinit var mainActivity: MainActivity
    private val adapter: UserAdapter by lazy {
        UserAdapter()
    }
    private var isViewShown: Boolean = false
    private val usersViewModel: UserViewModel by viewModels()
    private lateinit var fragmentUsersBinding: FragmentUsersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentUsersBinding = FragmentUsersBinding.inflate(inflater, container, false)
        return fragmentUsersBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        usersViewModel.apply {
            attachView(this@UsersFragment, this@UsersFragment)
            addObserver(this@UsersFragment)
        }
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fragmentUsersBinding.recyclerView.layoutManager = linearLayoutManager
        fragmentUsersBinding.recyclerView.adapter = adapter

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (view != null && menuVisible) {
            isViewShown = true
            if (mainActivity.getInternetConnectionStatus()) {
                fragmentUsersBinding.errorMessage.visibility = View.GONE
                usersViewModel.getUsers()
            } else {
                fragmentUsersBinding.errorMessage.visibility = View.VISIBLE
            }

        } else {
            isViewShown = false
            usersViewModel.cancelJob()
        }
    }


    override fun onProgress() {
        fragmentUsersBinding.progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(list: List<UserItem>) {
        adapter.items = list
    }

    override fun onProgressDismiss() {
        fragmentUsersBinding.progressBar.visibility = View.GONE
    }

    override fun onError(message: String) {
        fragmentUsersBinding.errorMessage.visibility = View.VISIBLE
        fragmentUsersBinding.errorMessage.text = message
    }
}
