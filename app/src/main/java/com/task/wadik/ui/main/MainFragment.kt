package com.task.wadik.ui.main

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.task.wadik.R
import com.task.wadik.adapters.tabadapter.ChildFragmentStateAdapter
import com.task.wadik.adapters.tabadapter.TabState
import com.task.wadik.base.BaseFragment
import com.task.wadik.databinding.FragmentMainBinding


class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding?.apply {
            val tabLayout = tabLayout
            val viewPager = viewPager
            viewPager.adapter =
                ChildFragmentStateAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
            // Bind tabs and viewpager
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                TabState.values().filter {
                    it.id == position
                }.map {
                    tab.text = it.tabName
                }
            }.attach()
        }
    }

}