package com.vegdev.vegacademy.model.data.models

class User {
    val likedRecipesId: MutableList<String>

    constructor() {
        likedRecipesId = mutableListOf()
    }

    constructor(likedRecipesId: MutableList<String>) {
        this.likedRecipesId = likedRecipesId
    }
}