package com.nikolaeva.healthdiary.db.model

import com.nikolaeva.healthdiary.model.ChallengeModel

data class UserFirebase(
    val uid: String,
    val name: String,
    val challenges: List<ChallengeModel>
)
