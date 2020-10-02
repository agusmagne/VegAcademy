package com.vegdev.vegacademy.model.data.models.users

class Org(
    var description: String = "",
    var location: String = "",
    var members: MutableList<User> = mutableListOf(),
    var videos: String = "",
    var articles: String = ""
)