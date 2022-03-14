package com.nikolaeva.healthdiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView


class ChallengesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challendes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter: ArrayAdapter<*>

        val users = arrayOf(

            "Challenge1", "Challenge2", "Challenge3",

            "Challenge4", "Challenge5"

        )

        // доступ к listView из файла XML

        var mListView = view.findViewById<ListView>(R.id.userList)

        /*arrayAdapter = ArrayAdapter(this,

            android.R.layout.simple_list_item_1, users)

        mListView.adapter = arrayAdapter*/
    }

    companion object {

        fun newInstance() = ChallengesFragment()

    }
}