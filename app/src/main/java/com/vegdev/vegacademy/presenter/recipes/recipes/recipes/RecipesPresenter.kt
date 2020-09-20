package com.vegdev.vegacademy.presenter.recipes.toprecipes

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.RecipesContract
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.RecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent.ParentRecipesAdapter
import com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent.ScrollStateHolder
import com.vegdev.vegacademy.view.main.main.MainView
import com.vegdev.vegacademy.view.recipes.recipes.RecipesView

class RecipesPresenter(
    private val context: Context,
    private val fragment: Fragment,
    private val view: RecipesContract.View,
    private val iMainView: MainView,
    private val interactor: RecipesInteractor
) {

    var parentAdapter: ParentRecipesAdapter? = null
    private var selectedRecipeDrawable: Drawable? = null

    suspend fun buildRVs(scrollStateHolder: ScrollStateHolder) {
        iMainView.showProgress()
        if (parentAdapter == null) {
            val allRecipeTypes = interactor.getAllRecipeTypes()!!
            parentAdapter = ParentRecipesAdapter(
                view,
                iMainView,
                allRecipeTypes,
                scrollStateHolder,
                // on child recipe click
                { recipe, drawable, view ->
                    navigate(recipe, drawable, view)
                }, {
                    view.startPostponedEnterTransition()
                })
        }
        view.buildRecipesParentRV(parentAdapter!!)
        iMainView.hideProgress()
    }

    private fun navigate(recipe: SingleRecipe, drawable: Drawable, view: View) {
        this.selectedRecipeDrawable = drawable

        val bundle = Bundle()
        bundle.putParcelable("recipe", recipe)
        bundle.putParcelable("src", drawable.toBitmap())

        val extras = FragmentNavigatorExtras(view to recipe.id)
        val options =
            NavOptions.Builder().setEnterAnim(R.anim.fragment_in_alpha)
                .setExitAnim(R.anim.fragment_out_slide_alpha)
                .build()
        fragment.findNavController()
            .navigate(R.id.navigation_recipe_details, bundle, options, extras)

//        val directions =
//            RecipesFragmentDirections.actionNavigationToprecipesToNavigationRecipeInfo(
//                recipe,
//                drawable.toBitmap()
//            )
//        iMainView.navigateToDirection(directions)
    }

    fun getCurrentRecipeDrawable(): Drawable? {
        if (selectedRecipeDrawable == null) return null
        val drawable = selectedRecipeDrawable
        selectedRecipeDrawable = null
        return drawable
    }

    fun openRecipeDetails(recipe: SingleRecipe, drawable: Drawable, view: View) {
        this.selectedRecipeDrawable = drawable

        val bundle = Bundle()
        bundle.putParcelable("recipe", recipe)
        bundle.putParcelable("src", drawable.toBitmap())

        val extras = FragmentNavigatorExtras(view to recipe.id)
        val options =
            NavOptions.Builder().setEnterAnim(R.anim.fragment_in_alpha)
                .setExitAnim(R.anim.fragment_out_slide_alpha)
                .build()
        fragment.findNavController()
            .navigate(R.id.navigation_recipe_details, bundle, options, extras)

    }
}