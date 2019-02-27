package com.example.adam.myapplication.ui.doctor.tabs

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.example.adam.myapplication.ui.doctor.create.DoctorTaskFragment
import com.example.adam.myapplication.ui.doctor.list.DoctorsFragment

class DoctorPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        private val TITLES = arrayOf("TASK", "DOCTORS", "HISTORY")
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return DoctorTaskFragment()

            1 -> return DoctorsFragment()

            2 -> return DoctorTaskFragment()
        }

        return null
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TITLES[position]
    }

    override fun getCount(): Int {
        return 3
    }
}
