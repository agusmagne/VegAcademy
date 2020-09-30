package com.vegdev.vegacademy.model.data.models

class User(
    var username: String = "",
    var email: String = "",
    val likedRecipesId: MutableList<String> = mutableListOf(),
    val organization: Organization = Organization(),
    var isUser: Boolean = false,
    var isOrganization: Boolean = false,
    var isAdmin: Boolean = false,
    val orgID: String = "",
    val orgName: String = ""
)