package com.nikolaeva.healthdiary.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import com.nikolaeva.healthdiary.R
import com.nikolaeva.healthdiary.model.CheckListModel

class DetailListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.getSerializable(DATA) as CheckListModel

        val title = view.findViewById<TextView>(R.id.txtNameList)
        val btnChallenge = view.findViewById<Button>(R.id.checkButton)
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)


       calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
           Toast.makeText(view.context, "$dayOfMonth/$month/$year", Toast.LENGTH_LONG).show()
       }

        // val currentMonth = YearMonth.now()
        // val firstMonth = currentMonth.minusMonths(10)
        // val lastMonth = currentMonth.plusMonths(10)
        // val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        // calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        // calendarView.scrollToMonth(currentMonth)
        //
        // calendarView.dayBinder = object : DayBinder<DayViewContainer> {
        //     // Called only when a new container is needed.
        //     override fun create(view: View) = DayViewContainer(view)
        //
        //     // Called every time we need to reuse a container.
        //     override fun bind(container: DayViewContainer, day: CalendarDay) {
        //         container.textView.text = day.date.dayOfMonth.toString()
        //     }
        // }


        // title.text = data.name
        // var countInt = data.count.toInt()
        // btnChallenge.setOnClickListener {
        //     countInt++
        // }
    }

    companion object {

        private const val DATA = "data"

        fun newInstance(checkListModel: CheckListModel) =
            DetailListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(DATA, checkListModel)
                }
            }
    }
}