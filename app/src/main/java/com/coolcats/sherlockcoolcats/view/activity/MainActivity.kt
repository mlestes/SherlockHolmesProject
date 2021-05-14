package com.coolcats.sherlockcoolcats.view.activity

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.util.myLog
import com.coolcats.sherlockcoolcats.view.adapter.MainViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainViewPagerAdapter

    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        adapter = MainViewPagerAdapter(supportFragmentManager)
        main_viewpager.adapter = adapter

        main_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //Do nothing...
            }

            override fun onPageSelected(position: Int) {
                myLog("Bottom nav: ${position.toString()}")
                when (position) {
                    0 -> bottom_nav.selectedItemId = R.id.add_cases_item
                    1 -> bottom_nav.selectedItemId = R.id.open_cases_item
                    else -> bottom_nav.selectedItemId = R.id.case_locations_item
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                //Do nothing
            }

        })

        bottom_nav.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.add_cases_item -> openFragment(0)
                R.id.open_cases_item -> openFragment(1)
                else -> openFragment(2)
            }
            true
        }

    }

    override fun onStart() {
        super.onStart()

        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), 700)
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 700) {
            if (permissions[0] == ACCESS_FINE_LOCATION) {
                if (grantResults[0] == PERMISSION_DENIED)
                    ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), 700)
            }
        }
    }

    private fun openFragment(frag: Int) {
        main_viewpager.setCurrentItem(frag, true)
    }
}