package com.nikolaeva.healthdiary.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.nikolaeva.healthdiary.INavigationFragment
import com.nikolaeva.healthdiary.R
import java.text.SimpleDateFormat
import java.util.*

class MainScreenFragment : Fragment() {

    private var listener: INavigationFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is INavigationFragment) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateTime = view.findViewById<TextView>(R.id.txtDateTime)
        val profileBtn = view.findViewById<Button>(R.id.btnProfile)
        val challengesBtn = view.findViewById<Button>(R.id.btnChallenges)
        val listsBtn = view.findViewById<Button>(R.id.btnLists)
        val plusBtn = view.findViewById<Button>(R.id.btnPlus)

        dateTime.text = getDateTime()

        profileBtn.setOnClickListener {
            listener?.goToProfileFragment()
        }

        challengesBtn.setOnClickListener {
            listener?.goToChallengesFragment()
        }

        listsBtn.setOnClickListener {
            listener?.goToListsFragment()
        }

        plusBtn.setOnClickListener {
            listener?.goToPlusFragment()
        }
    }

    private fun getDateTime(): String {
        val date = Date()
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val answer: String = formatter.format(date.time)
        return answer
    }

    companion object {

        fun newInstance() = MainScreenFragment()
    }

}
