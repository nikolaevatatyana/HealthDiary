package com.nikolaeva.healthdiary.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nikolaeva.healthdiary.R
import com.nikolaeva.healthdiary.db.FirebaseManager
import com.nikolaeva.healthdiary.db.model.UserFirebase
import com.nikolaeva.healthdiary.model.ChallengeModel
import com.nikolaeva.healthdiary.repositories.UserRepository

class DetailChallengeFragment : Fragment(), FirebaseManager.ReadDataCallback {

    private val userRepository = UserRepository()
    private lateinit var data: ChallengeModel
    private lateinit var count: TextView
    private var currentUser: UserFirebase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_challenge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = arguments?.getSerializable(DATA) as ChallengeModel

        val title = view.findViewById<TextView>(R.id.txtNameChallenge)
        count = view.findViewById<TextView>(R.id.countDays)
        val btnChallenge = view.findViewById<Button>(R.id.checkButton)

        userRepository.getCurrentUser(this)

        title.text = data.nameChallenge
        count.text = data.countChallenge
        var countInt = data.countChallenge.toInt()
        btnChallenge.setOnClickListener {
            countInt++
            count.text = countInt.toString()
            val a = currentUser
            if (a != null) {
                val list = a.challenges
                val modifierList = mutableListOf<ChallengeModel>()
                list.forEach { item ->
                    if (item.nameChallenge == data.nameChallenge) {
                        modifierList.add(ChallengeModel(data.nameChallenge, countInt.toString()))
                    } else {
                        modifierList.add(item)
                    }
                }
                userRepository.addUser(UserFirebase(a.uid, a.name, modifierList))
            }
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

    override fun readData(list: List<UserFirebase>) {
        currentUser = list.firstOrNull { userFirebase -> userFirebase.uid == Firebase.auth.currentUser?.uid }
        if (currentUser != null) {
            val currentChallenge = currentUser!!.challenges.find { challengeModel -> challengeModel.nameChallenge == data.nameChallenge }
            count.text = currentChallenge?.countChallenge
        }
    }
}