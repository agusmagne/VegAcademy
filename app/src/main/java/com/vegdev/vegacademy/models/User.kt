package com.vegdev.vegacademy.models

class User {
    val likedRecipesId: MutableList<String>

    constructor() {
        likedRecipesId = mutableListOf()
    }

    constructor(likedRecipesId: MutableList<String>) {
        this.likedRecipesId = likedRecipesId
    }
}