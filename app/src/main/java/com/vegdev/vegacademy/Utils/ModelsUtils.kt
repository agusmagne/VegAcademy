package com.vegdev.vegacademy.Utils

import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Category

class ModelsUtils {

    val VIDEOS_CATEGORY = "videos"

    val VEGACADEMY_COLLECTION = "veg"
    val VEGACADEMY_IMAGE = R.drawable.imageapp
    val VEGACADEMY_INSTAGRAM = "vegacademy"
    val VEGACADEMY_TITLE = "Academia Veg Introducción"

    val CEVA_COLLECTION = "ceva"
    val CEVA_IMAGE = R.drawable.image_ceva
    val CEVA_INSTAGRAM = "ceva.world"
    val CEVA_TITLE = "Abogacía Vegana Efectiva"

    val CARNISM_COLLECTION = "carn"
    val CARNISM_IMAGE = R.drawable.image_carnism
    val CARNISM_INSTAGRAM = "beyondcarnism"
    val CARNISM_TITLE = "Más Allá del Carnismo"

    val ARTICLES_CATEGORY = "art"

    val OTHERMOVEMENTS_COLLECTION = "om"
    val OTHERMOVEMENTS_IMAGE = R.drawable.othermovements
    val OTHERMOVEMENTS_TITLE = "Otros Movimientos"

    val COMUNICATION_COLLECTION = "co"
    val COMUNICATION_IMAGE = R.drawable.comunication
    val COMUNICATION_TITLE = "Sobre Comunicación"

    val NUTRITION_COLLECTION = "nu"
    val NUTRITION_IMAGE = R.drawable.nutrition
    val NUTRITION_TITLE = "Sobre Nutrición"
    val NUTRITION_INSTAGRAM = "conetica_"

    fun createVegAcademyCategory(): Category = Category(
        VIDEOS_CATEGORY,
        VEGACADEMY_COLLECTION,
        VEGACADEMY_IMAGE,
        VEGACADEMY_TITLE,
        VEGACADEMY_INSTAGRAM
    )

    fun createCevaCategory(): Category = Category(
        VIDEOS_CATEGORY, CEVA_COLLECTION, CEVA_IMAGE, CEVA_TITLE, CEVA_INSTAGRAM
    )

    fun createCarnismCategory(): Category = Category(
        VIDEOS_CATEGORY,
        CARNISM_COLLECTION,
        CARNISM_IMAGE,
        CARNISM_TITLE,
        CARNISM_INSTAGRAM
    )

    fun createOtherMovementsCategory(): Category = Category(
        ARTICLES_CATEGORY, OTHERMOVEMENTS_COLLECTION, OTHERMOVEMENTS_IMAGE, OTHERMOVEMENTS_TITLE, ""
    )

    fun createComunicationCategory(): Category = Category(
        ARTICLES_CATEGORY, COMUNICATION_COLLECTION, COMUNICATION_IMAGE, COMUNICATION_TITLE, ""
    )

    fun createNutritionCategory(): Category = Category(
        ARTICLES_CATEGORY,
        NUTRITION_COLLECTION,
        NUTRITION_IMAGE,
        NUTRITION_TITLE,
        NUTRITION_INSTAGRAM
    )
}