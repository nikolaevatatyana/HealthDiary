package com.nikolaeva.healthdiary.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nikolaeva.healthdiary.R
import com.nikolaeva.healthdiary.db.FirebaseManager
import com.nikolaeva.healthdiary.db.model.UserFirebase
import com.nikolaeva.healthdiary.model.ChallengeModel
import com.nikolaeva.healthdiary.model.CheckListModel
import com.nikolaeva.healthdiary.repositories.UserRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class DetailListFragment : Fragment(), FirebaseManager.ReadDataCallback {

    private val userRepository = UserRepository.getInstance()
    private lateinit var txtDates: TextView
    private lateinit var title: TextView
    private lateinit var data: CheckListModel
    private var currentUser: UserFirebase? = null
    private lateinit var btnCheckListDelete: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_list, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = arguments?.getSerializable(DATA) as CheckListModel

        title = view.findViewById<TextView>(R.id.txtNameCheckList)
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        txtDates = view.findViewById<TextView>(R.id.txtDates)

        title.text = data.nameCheckList
        showDates(data)



        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Toast.makeText(view.context, "$dayOfMonth/$month/$year", Toast.LENGTH_LONG).show()
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            val selectedDates: String = sdf.format(Date(year - 1900, month, dayOfMonth))
            val localUser = userRepository.getLocalUser()
            if (localUser != null) {
                val newCheckList = data.listDate.plus(listOf(selectedDates))
                val listCheckList = localUser.checkList
                val modifierList = mutableListOf<CheckListModel>()
                listCheckList?.forEach { item ->
                    if (item.nameCheckList == data.nameCheckList) {
                        modifierList.add(CheckListModel(data.nameCheckList, newCheckList))
                    } else {
                        modifierList.add(item)
                    }
                }
                userRepository.addUser(localUser.copy(checkList = modifierList))
                userRepository.getCurrentUser(this)
            }

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
        btnCheckListDelete.setOnClickListener {
            val user = currentUser
            if (user != null) {
                val list = user.checkList
                val modifierList = mutableListOf<CheckListModel>()
                list?.forEach { item ->
                    if (item.nameCheckList != data.nameCheckList) {
                        modifierList.add(item)
                    }
                }
              //  userRepository.addUser(UserFirebase(user.uid, user.name, modifierList, user.checkList))
                userRepository.getCurrentUser(this)
            }
        }
    }

    private fun showDates(data: CheckListModel) {
        val textDates = StringBuffer()
        data.listDate.forEach { date ->
            textDates.append(date).append("\n")
        }
        txtDates.text = textDates
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

    override fun readData(list: List<UserFirebase>) {
        val checkListDates = userRepository.getLocalUser()?.checkList?.find { checkList -> checkList.nameCheckList == title.text }
        if (checkListDates != null) {
            showDates(checkListDates)
        }
    }
}