package com.vegdev.vegacademy.models

class Video {
    val title: String
    val desc: String
    val src: String
    val link: String

    constructor(){
        this.title = ""
        this.desc = ""
        this.src = ""
        this.link = ""
    }

    constructor(title: String, desc: String, src: String, link: String) {
        this.title = title
        this.desc = desc
        this.src = src
        this.link = link
    }
}