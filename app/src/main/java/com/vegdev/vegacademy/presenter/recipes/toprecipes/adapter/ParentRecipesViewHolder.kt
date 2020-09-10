package com.vegdev.vegacademy.presenter.recipes.toprecipes.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.TopRecipesInteractor
import kotlinx.android.synthetic.main.single_parent_recipes.view.*

class ParentRecipesViewHolder(
    itemView: View,
    private val onRecipeClick: (recipe: SingleRecipe) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    val interactor = TopRecipesInteractor()
    private var childAdapter: TopRecipesAdapter? = null

    fun bindView(type: Any?) {
        itemView.type_txt.text = getStringResourceByName(type.toString())
        val recipesOptions = interactor.getPaginatedRecipesFromType(type)
        if (childAdapter == null) {
            childAdapter = TopRecipesAdapter(recipesOptions) {
                onRecipeClick(it)
            }
            itemView.child_recipes_rv.apply {
                this.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                this.adapter = childAdapter

            }
            childAdapter?.startListening()
        }
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
}