package com.nikolaeva.healthdiary.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nikolaeva.healthdiary.ui.adapters.ChallengesAdapter
import com.nikolaeva.healthdiary.ui.adapters.IChallengesAdapter
import com.nikolaeva.healthdiary.INavigationFragment
import com.nikolaeva.healthdiary.R
import com.nikolaeva.healthdiary.db.FirebaseManager
import com.nikolaeva.healthdiary.db.model.UserFirebase
import com.nikolaeva.healthdiary.model.ChallengeModel
import com.nikolaeva.healthdiary.repositories.UserRepository

class ChallengesFragment : Fragment(), IChallengesAdapter, FirebaseManager.ReadDataCallback {

    private var listener: INavigationFragment? = null
    private val userRepository = UserRepository()
    private lateinit var recyclerView: RecyclerView

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
        return inflater.inflate(R.layout.fragment_challendes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.chList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        userRepository.getCurrentUser(this)
    }

    private fun createUserFirebase(challenges: List<ChallengeModel>): UserFirebase {
        return UserFirebase(
            uid = Firebase.auth.currentUser?.uid.toString(),
            name = Firebase.auth.currentUser?.displayName.toString(),
            challenges = challenges
        )
    }

    private fun getDataList(): List<ChallengeModel> {
        val data = resources.getStringArray(R.array.challenges).toList()
        val challengeModelList = mutableListOf<ChallengeModel>()
        data.forEachIndexed { index, item ->
            challengeModelList.add(
                ChallengeModel(
                    nameChallenge = item,
                    countChallenge = 0.toString()
                )
            )
        }
        return challengeModelList
    }

    override fun itemClick(challengeModel: ChallengeModel) {
        listener?.goToDetailChallengeFragment(challengeModel)
    }

    companion object {

        fun newInstance() = ChallengesFragment()
    }

    override fun readData(list: List<UserFirebase>) {
        val currentUser = list.firstOrNull { userFirebase -> userFirebase.uid == Firebase.auth.currentUser?.uid }
        if (currentUser != null) {
            recyclerView.adapter = ChallengesAdapter(this, currentUser.challenges)
        } else {
            val userFirebase = createUserFirebase(getDataList())
            userRepository.addUser(userFirebase)
            recyclerView.adapter = ChallengesAdapter(this, userFirebase.challenges)
        }
    }
}