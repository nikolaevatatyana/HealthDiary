package com.nikolaeva.healthdiary.db.model

import com.nikolaeva.healthdiary.model.ChallengeModel
import com.nikolaeva.healthdiary.model.CheckListModel

data class UserFirebase(
    val uid: String,
    val name: String,
    val challenges: List<ChallengeModel>?,
    val checkList: List<CheckListModel>?
)
