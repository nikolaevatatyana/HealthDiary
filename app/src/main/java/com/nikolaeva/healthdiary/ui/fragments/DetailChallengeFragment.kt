package com.nikolaeva.healthdiary.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.nikolaeva.healthdiary.R
import com.nikolaeva.healthdiary.model.ChallengeModel

class DetailChallengeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_challenge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.getSerializable(DATA) as ChallengeModel

        val title = view.findViewById<TextView>(R.id.txtNameChallenge)
        val count = view.findViewById<TextView>(R.id.countDays)
        val btnChallenge = view.findViewById<Button>(R.id.checkButton)

        title.text = data.nameChallenge
        count.text = data.countChallenge
        var countInt = data.countChallenge.toInt()
        btnChallenge.setOnClickListener {
            countInt++
            count.text = countInt.toString()
        }
    }

    companion object {

        private const val DATA = "data"

        fun newInstance(challengeModel: ChallengeModel) =
            DetailChallengeFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(DATA, challengeModel)
                }
            }
    }
}