package com.vegdev.vegacademy.model.data.models.users


class User(
    var id: String ="",
    var username: String = "",
    var email: String = "",
    var likedRecipesId: MutableList<String> = mutableListOf(),
    var organization: Org? = null,
    var isUser: Boolean = false,
    var isAdmin: Boolean = false,
    var isOrg: Boolean = false,
    var orgRole: String = "activist"
)