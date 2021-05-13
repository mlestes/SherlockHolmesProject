package com.coolcats.sherlockcoolcats.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.coolcats.sherlockcoolcats.view.fragment.CaseLocationsFragment
import com.coolcats.sherlockcoolcats.view.fragment.UserInputFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager,
    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        if (position == 0) return UserInputFragment()
        else if (position == 1) return OpenCaseFragment()
        else return CaseLocationsFragment()
    }
}