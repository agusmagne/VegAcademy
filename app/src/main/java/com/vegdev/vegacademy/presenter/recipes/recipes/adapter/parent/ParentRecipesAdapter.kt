package com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.data.models.TypesRecipe
import com.vegdev.vegacademy.presenter.recipes.recipes.adapter.single.SingleRecipeAdapter
import kotlinx.android.synthetic.main.recipes_parent_single.view.*
import java.util.*


class ParentRecipesAdapter(
    private val types: TypesRecipe,
    private val onChildRecipeClick: (SingleRecipe, Drawable, View) -> Unit,
    private val onReturnRecipeImageLoaded: () -> Unit
) :
    RecyclerView.Adapter<ParentRecipesViewHolder>() {

    private val adapters: MutableList<SingleRecipeAdapter> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecipesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipes_parent_single, parent, false)
        return ParentRecipesViewHolder(itemView, { recipe, drawable, view ->
            onChildRecipeClick(recipe, drawable, view)
        }, {
            onReturnRecipeImageLoaded()
        })
    }

    override fun getItemCount(): Int = types.types.size

    override fun onBindViewHolder(holder: ParentRecipesViewHolder, position: Int) {
        val type: String = types.types[position]

        if (adapters.size >= position + 1) {
            holder.bindAdapter(type, adapters[position])
        } else {
            adapters.add(holder.createAdapter(type))
        }

        holder.itemView.recipe_parent_searchbar.setOnEditorActionListener { textView, i, keyEvent ->
            holder.onParentSearchBarAction(textView.text.toString().toLowerCase(Locale.ROOT).trim())
            false
        }

        holder.itemView.recipe_parent_searchbar.setOnTouchListener(
            holder.onTouchSearchBarIcon()
        )

        holder.itemView.recipe_parent_search_icon.setOnClickListener {
            holder.showSearchBar()
        }

        holder.itemView.recipe_parent_close_searchbar.setOnClickListener {
            holder.hideSearchBar()
        }

    }
}