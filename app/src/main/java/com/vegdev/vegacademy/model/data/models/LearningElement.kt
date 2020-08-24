package com.vegdev.vegacademy.model.data.models

import com.google.firebase.Timestamp

class LearningElement(
    val desc: String = "",
    val link: String = "",
    val date: Timestamp = Timestamp.now(),
    val icon: String = ""
) : Data()