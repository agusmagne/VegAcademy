package com.vegdev.vegacademy.presenter.recipes.dialogs

import android.app.Dialog
import com.vegdev.vegacademy.model.data.models.Recipe
import com.vegdev.vegacademy.model.domain.interactor.main.dialogs.RecipeSuggestionInteractor
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.main.MainView
import com.vegdev.vegacademy.view.recipes.dialogs.suggestion.RecipeSuggestionView

class RecipeSuggestionPresenter(
    private val mainView: MainView,
    private val iRecipeSuggestionView: RecipeSuggestionView,
    private val interactor: RecipeSuggestionInteractor
) {

    private val RECIPE_SUGGESTION_PROGRESS = "Enviando receta..."
    private val RECIPE_SUGGESTION_WARNING = "Todos los campos son obligatorios"
    private val RECIPE_SUGGESTION_SUCCESS = "¡Receta enviada!"
    private val RECIPE_SUGGESTION_ERROR = "Error al enviar receta"

    private val layoutUtils = LayoutUtils()

    fun suggestRecipe(recipe: Recipe) {
        if (recipe.title != "" && recipe.src != "") {
            mainView.makeToast(RECIPE_SUGGESTION_PROGRESS)
            iRecipeSuggestionView.dismiss()

            val task = interactor.suggestRecipe(recipe)
            task.addOnSuccessListener {
                mainView.makeToast(RECIPE_SUGGESTION_SUCCESS)
            }.addOnFailureListener {
                mainView.makeToast(RECIPE_SUGGESTION_ERROR)
            }
        } else {
            mainView.makeToast(RECIPE_SUGGESTION_WARNING)
        }
    }

    fun setLayoutParams(dialog: Dialog?) {

        dialog?.let {
            val width =
                dialog.context.resources.displayMetrics.widthPixels - layoutUtils.toPx(32)
            val height =
                dialog.context.resources.displayMetrics.heightPixels - layoutUtils.toPx(380)

            val params = dialog.window?.attributes
            params?.width = width
            params?.height = height

            iRecipeSuggestionView.setLayoutParams(params)
        }
    }

}