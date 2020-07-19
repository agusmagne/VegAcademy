package com.vegdev.vegacademy.models

import com.google.firebase.Timestamp

class LearningElement {
    val title: String
    val desc: String
    val src: String
    val link: String
    val date: Timestamp
    val cat: String

    constructor(){
        this.title = ""
        this.desc = ""
        this.src = ""
        this.link = ""
        this.date = Timestamp.now()
        this.cat = ""
    }

    constructor(title: String, desc: String, src: String, link: String, date: Timestamp, cat: String) {
        this.title = title
        this.desc = desc
        this.src = src
        this.link = link
        this.date = date
        this.cat = cat
    }
}