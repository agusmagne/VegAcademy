package com.vegdev.vegacademy.presenter.recipes.recipes.single.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.recipes.RecipesContract
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.recipes.single.SingleRecipePresenter
import com.vegdev.vegacademy.view.main.main.MainView
import kotlinx.android.synthetic.main.recipes_child_single.view.*

class SingleRecipeAdapter(
    options: FirestorePagingOptions<SingleRecipe>,
    iRecipesView: RecipesContract.View,
    iMainView: MainView
) : FirestorePagingAdapter<SingleRecipe, SingleRecipeViewHolder>(options) {

    private val presenter = SingleRecipePresenter(iRecipesView, iMainView, iMainView.getUserInfo().likedRecipesId)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRecipeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recipes_child_single, parent, false)
        return SingleRecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: SingleRecipeViewHolder,
        position: Int,
        recipe: SingleRecipe
    ) {
        presenter.handleSingleRecipeLoading(recipe, holder)

        holder.itemView.likes.setOnClickListener { presenter.onLikesButtonClick(recipe, holder) }
        holder.itemView.src.transitionName = recipe.id
        holder.itemView.src.setOnClickListener {
            presenter.onSingleRecipeClick(recipe, holder.itemView.src.drawable, holder.itemView.src)
        }
    }
}