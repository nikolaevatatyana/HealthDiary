package com.nikolaeva.healthdiary.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nikolaeva.healthdiary.db.FirebaseManager
import com.nikolaeva.healthdiary.db.model.UserFirebase
import com.nikolaeva.healthdiary.model.ChallengeModel
import com.nikolaeva.healthdiary.model.CheckListModel

class UserRepository {

    private val firebaseManager = FirebaseManager()
    private var userFirebaseLocal: UserFirebase? = null

    fun createUserFirebase(challenges: List<ChallengeModel>? = null, checkList: List<CheckListModel>? = null): UserFirebase {
        return if (userFirebaseLocal != null) {
            UserFirebase(
                uid = Firebase.auth.currentUser?.uid.toString(),
                name = Firebase.auth.currentUser?.displayName.toString(),
                challenges = challenges ?: userFirebaseLocal?.challenges,
                checkList = checkList ?: userFirebaseLocal?.checkList
            )
        } else {
            UserFirebase(
                uid = Firebase.auth.currentUser?.uid.toString(),
                name = Firebase.auth.currentUser?.displayName.toString(),
                challenges = challenges,
                checkList = checkList
            )
        }
    }

    fun getLocalUser() = userFirebaseLocal

    fun updateLocalUser(userFirebase: UserFirebase) {
        userFirebaseLocal = userFirebase
    }

    fun addUser(userFirebase: UserFirebase) {
        userFirebaseLocal = userFirebase
        return firebaseManager.addUser(userFirebase, null)
    }

    fun getCurrentUser(readDataCallback: FirebaseManager.ReadDataCallback?) {
        return firebaseManager.getAllUsers(readDataCallback)
    }


    companion object {
        private var INSTANCE: UserRepository? = null

        fun getInstance(): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository()
            }
            return INSTANCE as UserRepository
        }
    }
}