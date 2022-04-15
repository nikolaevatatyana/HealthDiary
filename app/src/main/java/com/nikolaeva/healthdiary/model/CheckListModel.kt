package com.nikolaeva.healthdiary.model

import java.io.Serializable

class CheckListModel (
    val nameCheckList: String,
    val listDate: List<String>
) : Serializable