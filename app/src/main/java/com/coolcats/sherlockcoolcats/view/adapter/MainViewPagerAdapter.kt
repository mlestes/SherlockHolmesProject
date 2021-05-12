package com.coolcats.sherlockcoolcats.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.coolcats.sherlockcoolcats.view.fragment.AppFragment
import com.coolcats.sherlockcoolcats.view.fragment.CaseLocationsFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> AppFragment.getInstance(AppFragment.SherlockFragment.NEW_CASES)
            1 -> AppFragment.getInstance(AppFragment.SherlockFragment.OPEN_CASES)
            else -> CaseLocationsFragment()
        }
    }
}