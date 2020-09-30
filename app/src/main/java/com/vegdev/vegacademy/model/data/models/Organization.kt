package com.vegdev.vegacademy.model.data.models

class Organization(
    val id: String = "",
    val name: String = "",
    val members: MutableList<User> = mutableListOf()
)