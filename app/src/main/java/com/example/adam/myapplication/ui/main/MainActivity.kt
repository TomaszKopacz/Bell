package com.example.adam.myapplication.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.adam.myapplication.R
import com.example.adam.myapplication.ui.board.BoardFragment
import com.example.adam.myapplication.ui.doctor.tabs.DoctorTabsFragment
import com.example.adam.myapplication.ui.drugs.drugs_tabs.DrugsTabsFragment
import com.example.adam.myapplication.ui.scores.scores_tabs.ScoresTabsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
        changeContentView(BoardFragment())
    }

    private fun setListeners() {
        setBottomNavigationListener()
    }

    private fun setBottomNavigationListener() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true

            when (menuItem.itemId) {
                R.id.action_board -> changeContentView(BoardFragment())

                R.id.action_doctor -> changeContentView(DoctorTabsFragment())

                R.id.action_pill -> changeContentView(DrugsTabsFragment())

                R.id.action_measurement -> changeContentView(ScoresTabsFragment())
            }
            false
        }
    }

    private fun changeContentView(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    fun goToBoard() {
        bottom_navigation.selectedItemId = R.id.action_board
        changeContentView(BoardFragment())
    }
}
