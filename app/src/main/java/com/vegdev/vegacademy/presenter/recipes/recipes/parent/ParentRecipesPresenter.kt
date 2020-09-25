package com.vegdev.vegacademy.presenter.recipes.recipes.parent

import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.google.android.gms.common.util.Strings
import com.vegdev.vegacademy.contract.recipes.ParentRecipeContract
import com.vegdev.vegacademy.contract.recipes.RecipesContract
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.RecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.recipes.parent.adapter.ParentRecipesViewHolder
import com.vegdev.vegacademy.presenter.recipes.recipes.single.adapter.SingleRecipeAdapter

class ParentRecipesPresenter(
    private val iRecipesView: RecipesContract.View
) : ParentRecipeContract.Actions {

    private val interactor = RecipesInteractor()
    private val adapters: MutableList<SingleRecipeAdapter> = mutableListOf()
    private val isOptionsBasic = mutableListOf<Boolean>()

    override fun bindViewHolder(type: String, position: Int, iHolder: ParentRecipeContract.View) {
        if (adapters.size < position + 1) {
            adapters.add(createAdapter(type))
            isOptionsBasic.add(true)
        }
        iHolder.bindViewAndBindAdapter(
            type,
            adapters[position]
        )
    }

    private fun createAdapter(type: String): SingleRecipeAdapter {
        val recipesOptions = interactor.getPaginatedRecipesFromType(type)
        return SingleRecipeAdapter(recipesOptions, iRecipesView)
    }

    override fun handleSearchBarAction(
        text: String,
        type: String,
        position: Int,
        holder: ParentRecipesViewHolder
    ): Boolean {
        if (!Strings.isEmptyOrWhitespace(text)) {
            isOptionsBasic[position] = false
            val options = interactor.getPaginatedFilteredRecipesFromType(text, type)
            adapters[position].updateOptions(options)
        } else {
            iRecipesView.makeToast("Debes ingresar un nombre o ingrediente")
        }
        return false
    }

    override fun onTouchSearchBarIcon(
        type: String,
        position: Int,
        holder: ParentRecipesViewHolder
    ): View.OnTouchListener? {
        return View.OnTouchListener { v, e ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (e.action == MotionEvent.ACTION_UP) {
                if (e.rawX >= v.right - (v as EditText).compoundDrawables[DRAWABLE_RIGHT].bounds.width()
                ) {
                    handleSearchBarAction(v.editableText.toString().trim(), type, position, holder)
                    return@OnTouchListener true
                }
            }
            v.performClick()
            false
        }
    }

    override fun showSearchBar(holder: ParentRecipesViewHolder) {

    }

    override fun hideSearchBar(position: Int, holder: ParentRecipesViewHolder) {

    }
}