package com.vegdev.vegacademy.models

import com.google.firebase.Timestamp

class LearningElement {
    val title: String
    val desc: String
    val src: String
    val link: String
    val date: Timestamp

    constructor(){
        this.title = ""
        this.desc = ""
        this.src = ""
        this.link = ""
        this.date = Timestamp.now()
    }

    constructor(title: String, desc: String, src: String, link: String, date: Timestamp) {
        this.title = title
        this.desc = desc
        this.src = src
        this.link = link
        this.date = date
    }
}