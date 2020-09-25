package com.vegdev.vegacademy.presenter.recipes.recipes.parent.adapter

import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.google.android.gms.common.util.Strings
import com.vegdev.vegacademy.contract.recipes.ParentRecipeContract
import com.vegdev.vegacademy.contract.recipes.RecipesContract
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.RecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.recipes.single.adapter.SingleRecipeAdapter
import com.vegdev.vegacademy.utils.Utils
import kotlinx.android.synthetic.main.recipes_parent_single.view.*

class ParentRecipesViewHolder(
    itemView: View,
    private val iRecipesView: RecipesContract.View,
    private val scrollStateHolder: ScrollStateHolder
) : RecyclerView.ViewHolder(itemView), ScrollStateHolder.ScrollStateKeyProvider, ParentRecipeContract.View {

    val interactor = RecipesInteractor()
    private lateinit var adapter: SingleRecipeAdapter
    private lateinit var type: String
    private var isCurrentOptionsBasic = true
    private val utils = Utils()

    override fun bindViewAndBindAdapter(type: String, adapter: SingleRecipeAdapter) {
        this.type = type
        this.adapter = adapter
        itemView.type_txt.text = utils.getStringResourceByName(type, itemView.context)
        itemView.child_recipes_rv?.apply {
            this.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
        scrollStateHolder.setupRecyclerView(itemView.child_recipes_rv, this)
        scrollStateHolder.restoreScrollState(itemView.child_recipes_rv, this)
        adapter.startListening()
    }

    override fun showSearchBar() {
        TransitionManager.beginDelayedTransition(itemView.recipe_child_root)
        itemView.recipe_parent_searchbar.visibility = View.VISIBLE
        itemView.type_txt.visibility = View.INVISIBLE
        itemView.recipe_parent_search_icon.visibility = View.INVISIBLE
    }

    override fun hideSearchBar() {
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

    override fun getScrollStateKey(): String? = type
}