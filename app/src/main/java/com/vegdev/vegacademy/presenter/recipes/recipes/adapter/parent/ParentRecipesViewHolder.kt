package com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent

import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.TopRecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.recipes.adapter.single.SingleRecipeAdapter
import kotlinx.android.synthetic.main.recipes_parent_single.view.*

class ParentRecipesViewHolder(
    itemView: View,
    private val onRecipeClick: (SingleRecipe, Drawable) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    val interactor = TopRecipesInteractor()
    private var childAdapter: SingleRecipeAdapter? = null

    fun bindView(type: Any?) {
        itemView.type_txt.text = getStringResourceByName(type.toString())
        val recipesOptions = interactor.getPaginatedRecipesFromType(type)
        if (childAdapter == null) {
            childAdapter = SingleRecipeAdapter(recipesOptions) { recipe, drawable ->
                onRecipeClick(recipe, drawable)
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