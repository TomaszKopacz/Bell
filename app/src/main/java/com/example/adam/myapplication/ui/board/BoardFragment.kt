package com.example.adam.myapplication.ui.board


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adam.myapplication.R
import com.example.adam.myapplication.ui.lists.TaskAdapter
import com.example.adam.myapplication.utils.Picker
import kotlinx.android.synthetic.main.fragment_board.*
import java.text.DateFormat
import java.util.*

class BoardFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var viewModel: BoardViewModel

    private var adapter: TaskAdapter = TaskAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        layout = inflater.inflate(R.layout.fragment_board, container, false)
        viewModel = ViewModelProviders.of(this).get(BoardViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        setObservers()
        setListeners()
    }

    private fun initRecyclerView() {
        list_view.layoutManager = LinearLayoutManager(activity)
        list_view.adapter = adapter
    }

    private fun setObservers() {
        setCalendarObserver()
        setTasksObserver()
    }

    private fun setCalendarObserver() {
        viewModel.calendar.observe(this, Observer { calendar ->
            displayDate(calendar ?: getCurrentDate())
        })
    }

    private fun displayDate(calendar: Calendar) {
        val date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        year_label.text = date.substring(date.length - 4)
        day_month_label?.text = date.substring(0, date.length - 5)
    }

    private fun getCurrentDate(): Calendar {
        return Calendar.getInstance()
    }

    private fun setTasksObserver() {
        viewModel.list.observe(this, Observer { tasks ->

            if (tasks != null && tasks.isNotEmpty()) {
                hideViewForEmptyBoard()
                adapter.loadTasks(tasks)
            } else
                showViewForEmptyBoard()
        })
    }

    private fun showViewForEmptyBoard() {
        no_tasks_view.visibility = View.VISIBLE
        list_view.visibility = View.GONE
    }

    private fun hideViewForEmptyBoard() {
        no_tasks_view.visibility = View.GONE
        list_view.visibility = View.VISIBLE
    }

    private fun setListeners() {
        setCalendarListener()
    }

    private fun setCalendarListener() {
        fab_calendar.setOnClickListener { showCalendarView() }
    }

    private fun showCalendarView() {
        Picker.showDatePicker(context) { _, year, month, day ->
            val date = Calendar.getInstance()
            date.set(Calendar.YEAR, year)
            date.set(Calendar.MONTH, month)
            date.set(Calendar.DAY_OF_MONTH, day)

            viewModel.dateSet(date)
        }
    }
}
