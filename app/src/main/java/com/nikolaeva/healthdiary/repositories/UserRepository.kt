package com.nikolaeva.healthdiary.repositories

import com.nikolaeva.healthdiary.db.FirebaseManager
import com.nikolaeva.healthdiary.db.model.UserFirebase

class UserRepository {

    private val firebaseManager = FirebaseManager()

    fun addUser(userFirebase: UserFirebase) {
        return firebaseManager.addUser(userFirebase, null)
    }

    fun getCurrentUser(readDataCallback: FirebaseManager.ReadDataCallback?) {
        return firebaseManager.getAllUsers(readDataCallback)
    }
}