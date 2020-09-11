package com.vegdev.vegacademy.presenter.recipes.toprecipes

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.RecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent.ParentRecipesAdapter
import com.vegdev.vegacademy.view.main.main.MainView
import com.vegdev.vegacademy.view.recipes.recipes.RecipesView

class RecipesPresenter(
    private val context: Context,
    private val fragment: Fragment,
    private val view: RecipesView,
    private val iMainView: MainView,
    private val interactor: RecipesInteractor
) {

    var parentAdapter: ParentRecipesAdapter? = null

    suspend fun buildRVs() {
        iMainView.showProgress()
        if (parentAdapter == null) {
            val allRecipeTypes = interactor.getAllRecipeTypes()!!
            parentAdapter = ParentRecipesAdapter(
                allRecipeTypes
                // on child recipe click
            ) { recipe, drawable, view ->
                navigate(recipe, drawable, view)
            }
        }
        view.buildRecipesParentRV(parentAdapter!!)
        iMainView.hideProgress()
    }

    private fun navigate(recipe: SingleRecipe, drawable: Drawable, view: View) {
        val bundle = Bundle()
        bundle.putParcelable("recipe", recipe)
        bundle.putParcelable("src", drawable.toBitmap())

        val extras = FragmentNavigatorExtras(view to recipe.id)
        fragment.findNavController().navigate(R.id.navigation_recipe_details, bundle, null, extras)

//        val directions =
//            RecipesFragmentDirections.actionNavigationToprecipesToNavigationRecipeInfo(
//                recipe,
//                drawable.toBitmap()
//            )
//        iMainView.navigateToDirection(directions)
    }
}