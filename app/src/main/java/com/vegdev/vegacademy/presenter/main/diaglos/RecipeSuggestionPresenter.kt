package com.vegdev.vegacademy.presenter.main.diaglos

import android.content.Context
import com.vegdev.vegacademy.model.data.models.Recipe
import com.vegdev.vegacademy.view.main.main.IMainView

class RecipeSuggestionPresenter(val context: Context, val iMainView: IMainView) {

    private val SENDING_RECIPE_TEXT = "Enviando receta..."
    private val COMPLETE_ALL_FIELDS_WARNING = "Todos los campos son obligatorios"

    fun suggestRecipe(
        name: String,
        image: String,
        ingredients: String,
        taste: String,
        meal: String
    ) {

        iMainView.makeToast(SENDING_RECIPE_TEXT)
        iMainView.suggestRecipe(
            Recipe(name, image, ingredients, taste, meal, 0, "")
        )
    }

}