package com.nikolaeva.healthdiary.model

import java.io.Serializable

data class ChallengeModel(
    val nameChallenge: String,
    val countChallenge: String,
    val dateChallenge: String //20.05.2022
) : Serializable
