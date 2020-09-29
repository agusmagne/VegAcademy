package com.vegdev.vegacademy.model.data.models

class User(
    val username: String = "",
    val likedRecipesId: MutableList<String> = mutableListOf(),
    val organization: Organization = Organization()
)