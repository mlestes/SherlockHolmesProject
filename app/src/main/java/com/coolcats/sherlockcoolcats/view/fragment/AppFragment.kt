package com.coolcats.sherlockcoolcats.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coolcats.sherlockcoolcats.R

class AppFragment: Fragment() {

    enum class SherlockFragment {
        OPEN_CASES,
        NEW_CASES,
        CASE_LOCATIONS
    }
    //how to declare static variable
    companion object{
        const val KEY = "FRAG_KEY"
        fun getInstance(fragment: SherlockFragment): AppFragment {
            return AppFragment().also { frag ->
                frag.arguments = Bundle().also { bndle ->
                    bndle.putString(KEY, fragment.name)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View? = null

        arguments?.getString(KEY)?.let { name ->

                view = when(SherlockFragment.valueOf(name)){

                     SherlockFragment.OPEN_CASES -> inflater.inflate(R.layout.open_cases_fragment_layout, container, false)

                    SherlockFragment.NEW_CASES -> inflater.inflate(R.layout.closed_cases_fragment_layout, container, false)

                    else ->
                        inflater.inflate(R.layout.case_locations_fragment_layout, container, false)
                 }
        }
        return view
    }

}