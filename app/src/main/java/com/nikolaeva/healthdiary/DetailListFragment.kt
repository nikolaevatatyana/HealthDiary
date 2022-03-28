package com.nikolaeva.healthdiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.nikolaeva.healthdiary.model.ChallengeModel
import com.nikolaeva.healthdiary.model.ListModel


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

        val data = arguments?.getSerializable(DATA) as ListModel

        val title = view.findViewById<TextView>(R.id.nameList)
        val count = view.findViewById<TextView>(R.id.dateList)
        val btnChallenge = view.findViewById<Button>(R.id.checkButton)

        title.text = data.nameList
        count.text = data.countList
        var countInt = data.countList.toInt()
        btnChallenge.setOnClickListener {
            countInt++
            count.text = countInt.toString()
        }
    }

    companion object {

        private const val DATA = "data"

        fun newInstance(listModel: ListModel) =
            DetailListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(DATA, listModel)
                }
            }
    }
}