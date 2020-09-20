package com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent

import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.vegdev.vegacademy.contract.RecipesContract
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.RecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.recipes.adapter.single.SingleRecipeAdapter
import com.vegdev.vegacademy.view.main.main.MainView
import com.vegdev.vegacademy.view.recipes.recipes.RecipesView
import kotlinx.android.synthetic.main.recipes_parent_single.view.*

class ParentRecipesViewHolder(
    itemView: View,
    private val iRecipesView: RecipesContract.View,
    private val iMainView: MainView,
    private val scrollStateHolder: ScrollStateHolder,
    private val onRecipeClick: (SingleRecipe, Drawable, View) -> Unit,
    private val onReturnRecipeImageLoaded: () -> Unit
) : RecyclerView.ViewHolder(itemView), ScrollStateHolder.ScrollStateKeyProvider {

    val interactor = RecipesInteractor()
    private lateinit var adapter: SingleRecipeAdapter
    private lateinit var type: String
    private var isCurrentOptionsBasic = true

    fun createAdapter(type: Any?): SingleRecipeAdapter {
        val recipesOptions = interactor.getPaginatedRecipesFromType(type)
        val childAdapter = SingleRecipeAdapter(recipesOptions, iRecipesView, iMainView, { recipe, drawable, view ->
            onRecipeClick(recipe, drawable, view)
        }, {
            onReturnRecipeImageLoaded()
        })
        childAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        bindAdapter(type.toString(), childAdapter)
        return childAdapter
    }

    fun bindAdapter(type: String, adapter: SingleRecipeAdapter) {
        this.type = type
        this.adapter = adapter
        itemView.type_txt.text = getStringResourceByName(type)
        itemView.child_recipes_rv?.apply {
            this.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
        scrollStateHolder.setupRecyclerView(itemView.child_recipes_rv, this)
        scrollStateHolder.restoreScrollState(itemView.child_recipes_rv, this)
        adapter.startListening()
    }

    private fun getStringResourceByName(name: String): String {
        return itemView.context.getString(
            itemView.context.resources.getIdentifier(
                name,
                "string",
                itemView.context.packageName
            )
        )
    }

    fun showSearchBar() {
        TransitionManager.beginDelayedTransition(itemView.recipe_child_root)
        itemView.recipe_parent_searchbar.visibility = View.VISIBLE
        itemView.type_txt.visibility = View.INVISIBLE
        itemView.recipe_parent_search_icon.visibility = View.INVISIBLE
    }

    fun hideSearchBar() {
        TransitionManager.beginDelayedTransition(itemView.recipe_child_root)
        itemView.recipe_parent_searchbar.visibility = View.GONE
        itemView.recipe_parent_searchbar.editableText.clear()
        itemView.type_txt.visibility = View.VISIBLE
        itemView.recipe_parent_search_icon.visibility = View.VISIBLE
        if (!isCurrentOptionsBasic) {
            val options = interactor.getPaginatedRecipesFromType(type)
            adapter.updateOptions(options)
            this.isCurrentOptionsBasic = true
        }
    }

    fun onTouchSearchBarIcon(): View.OnTouchListener? {
        return View.OnTouchListener { v, e ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (e.action == MotionEvent.ACTION_UP) {
                if (e.rawX >= v.right - (v as EditText).compoundDrawables[DRAWABLE_RIGHT].bounds.width()
                ) {
                    onParentSearchBarAction(v.editableText.toString().trim())
                    return@OnTouchListener true
                }
            }
            v.performClick()
            false
        }
    }

    fun onParentSearchBarAction(string: String) {
        if (string != "") {
            this.isCurrentOptionsBasic = false
            val options = interactor.getPaginatedFilteredRecipesFromType(string, type)
            adapter.updateOptions(options)
        } else {
            Toast.makeText(
                itemView.context,
                "Debes ingresar un nombre o ingrediente",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun getScrollStateKey(): String? = type
}