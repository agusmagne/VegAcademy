package com.vegdev.vegacademy.model.data.models.users

class Org(
    var description: String = "",
    var location: String = "",
    var members: MutableList<Member> = mutableListOf(),
    var videos: String = "",
    var articles: String = "",
    var showMembers: Boolean = true,
    var showContact: Boolean = true
)