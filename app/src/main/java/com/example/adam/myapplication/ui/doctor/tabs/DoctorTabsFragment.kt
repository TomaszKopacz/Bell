package com.example.adam.myapplication.ui.doctor.tabs


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.adam.myapplication.R

class DoctorTabsFragment : Fragment() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_doctor_tabs, container, false)

        customizeComponents(view)

        return view
    }

    private fun customizeComponents(view: View) {
        tabLayout = view.findViewById(R.id.tabs)
        viewPager = view.findViewById(R.id.view_pager)

        makeTabPages()
    }

    private fun makeTabPages() {
        val adapter = DoctorPagerAdapter(childFragmentManager)
        viewPager!!.adapter = adapter
        tabLayout!!.setupWithViewPager(viewPager)
    }
}
