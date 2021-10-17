package com.task.wadik.adapters.tabadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IllegalArgumentException

class ChildFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = TabState.values().size

    override fun createFragment(position: Int): Fragment {
        return findFragment(position)
            ?: throw IllegalArgumentException("Position $position not valid")
    }

    private fun findFragment(position: Int): Fragment? {
        TabState.values().filter {
            it.id == position
        }.map {
            return it.fragment
        }
        return null
    }


}