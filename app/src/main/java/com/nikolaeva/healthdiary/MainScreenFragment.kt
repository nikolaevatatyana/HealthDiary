package com.nikolaeva.healthdiary

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainScreenFragment : Fragment() {

    private var listener: INavigationFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is INavigationFragment){
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

        dateTime.text = getDateTime()

        val profileBtn = view.findViewById<Button>(R.id.btnProfile)

        profileBtn.text = "Профиль"

        profileBtn.setOnClickListener {
            listener?.goToProfileFragment()

            val challengesBtn = view.findViewById<Button>(R.id.btnChallenges)

            challengesBtn.text = "Challenges"

            challengesBtn.setOnClickListener {
                listener?.goToChallengesFragment()
            }
            val listsBtn = view.findViewById<Button>(R.id.btnLists)

            listsBtn.text = "Lists"

            listsBtn.setOnClickListener {
                listener?.goToListsFragment()
            }

            val loginBtn = view.findViewById<Button>(R.id.btnLogin)

            loginBtn.text = "Войти"

            /*loginBtn.setOnClickListener {
                val text = "Вход упешно выполнен"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

            }*/

        }
    }
        fun getDateTime(): String {
            val date = Date()
            val formatter = SimpleDateFormat("dd.MM.yyyy")
            val answer: String = formatter.format(date.time)
            return answer
        }

        companion object {

            fun newInstance() = MainScreenFragment()
        }

    }
