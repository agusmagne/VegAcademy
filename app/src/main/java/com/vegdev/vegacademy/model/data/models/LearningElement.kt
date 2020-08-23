package com.vegdev.vegacademy.model.data.models

import com.google.firebase.Timestamp

class LearningElement(
    val title: String = "",
    val desc: String = "",
    val src: String = "",
    val link: String = "",
    val date: Timestamp = Timestamp.now(),
    val cat: String = "",
    val icon: String = ""
)