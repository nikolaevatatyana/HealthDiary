package com.nikolaeva.healthdiary.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nikolaeva.healthdiary.R
import com.nikolaeva.healthdiary.db.FirebaseManager
import com.nikolaeva.healthdiary.db.model.UserFirebase
import com.nikolaeva.healthdiary.model.ChallengeModel
import com.nikolaeva.healthdiary.repositories.UserRepository

class PlusFragment : Fragment(), FirebaseManager.ReadDataCallback {

    private val userRepository = UserRepository.getInstance()
    private lateinit var editName: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plus, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRepository.getCurrentUser(this)
        editName = view.findViewById<EditText>(R.id.txtPlus)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        val createItem = view.findViewById<AppCompatButton>(R.id.btn_create)

        var isCheckList = false

        radioGroup.check(R.id.radio_challenge)


        radioGroup.setOnCheckedChangeListener { group, id ->
            when (group.checkedRadioButtonId) {
                R.id.radio_checkList -> isCheckList = true
                R.id.radio_challenge -> isCheckList = false
            }
        }

        createItem.setOnClickListener {
            if (isCheckList) {

            } else {
                addChallenge()
            }

        }
    }

    private fun addChallenge() {
        val currentUser = userRepository.getLocalUser()
        val currentChallengeList = currentUser?.challenges
        val newListChallenge = currentChallengeList?.plus(listOf(ChallengeModel(nameChallenge = editName.text.toString(), countChallenge = "0")))
        newListChallenge?.let {
            val userFirebase = userRepository.createUserFirebase(challenges = newListChallenge)
            userRepository.addUser(userFirebase)
            editName.text.clear()
        }
    }

    override fun readData(list: List<UserFirebase>) {
        val currentUser = list.firstOrNull { userFirebase -> userFirebase.uid == Firebase.auth.currentUser?.uid }
        if (currentUser != null) {
            userRepository.updateLocalUser(currentUser)
        }
    }

    companion object {

        fun newInstance() = PlusFragment()
    }
}