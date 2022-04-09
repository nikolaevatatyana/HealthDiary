package com.nikolaeva.healthdiary.db

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nikolaeva.healthdiary.db.model.UserFirebase
import com.nikolaeva.healthdiary.model.ChallengeModel

class FirebaseManager {
    val db = Firebase.database.getReference(LIST_USER)
    val auth = Firebase.auth

    fun addUser(userFirebase: UserFirebase, finishListener: FinishWorkListener?) {

        if (auth.uid != null) db.child(auth.uid ?: "empty")
            .child(USER_NODE).setValue(userFirebase).addOnCompleteListener {
                finishListener?.onFinish()
            }
    }

    fun getAllUsers(readDataCallback: ReadDataCallback?) {
        val query = db.orderByChild(auth.uid + "/user")
        readDataFromDb(query, readDataCallback)
    }

    private fun readDataFromDb(query: Query, readDataCallback: ReadDataCallback?) {
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listUsers = ArrayList<UserFirebase>()
                for (item in snapshot.children) {
                    var user: UserFirebase? = null
                    item.children.forEach {
                        if (user == null) {
                            // user = it.getValue(UserFirebase::class.java)
                            val name = it.child("name").value
                            val uid = it.child("uid").value

                            val list = arrayListOf<ChallengeModel>()
                            it.child("challenges").children.forEach { challenge ->
                                list.add(
                                    ChallengeModel(
                                        challenge.child("nameChallenge").value.toString(),
                                        challenge.child("countChallenge").value.toString()
                                    )
                                )
                            }

                            user = UserFirebase(
                                name = name.toString(),
                                uid = uid.toString(),
                                challenges = list
                            )
                        }
                    }
                    user?.let { listUsers.add(it) }
                }
                readDataCallback?.readData(listUsers)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    interface ReadDataCallback {
        fun readData(list: List<UserFirebase>)
    }

    interface FinishWorkListener {
        fun onFinish()
    }

    companion object {
        const val USER_NODE = "user"
        const val LIST_USER = "users"
    }
}