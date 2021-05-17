package com.coolcats.sherlockcoolcats.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.coolcats.sherlockcoolcats.util.myLog
import com.coolcats.sherlockcoolcats.view.fragment.CaseLocationsFragment
import com.coolcats.sherlockcoolcats.view.fragment.OpenCasesFragment
import com.coolcats.sherlockcoolcats.view.fragment.UserInputFragment


class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager,
    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        myLog("ViewPager: ${position.toString()}")
        return when (position) {
            0 -> UserInputFragment()
            1 -> OpenCasesFragment()
            else -> CaseLocationsFragment()
        }
    }

//    fun submitList(it: MutableList<Case>) {
//
//    }

}
