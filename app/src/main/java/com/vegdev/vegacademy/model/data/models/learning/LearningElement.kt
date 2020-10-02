package com.vegdev.vegacademy.model.data.models.learning

import com.google.firebase.Timestamp

class LearningElement(
    val desc: String = "",
    val link: String = "",
    val date: Timestamp = Timestamp.now(),
    val icon: String = "",
    var title: String = "",
    var src: String = "",
    var cat: String = ""
)