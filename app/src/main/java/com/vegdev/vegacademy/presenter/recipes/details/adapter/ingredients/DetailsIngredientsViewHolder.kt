package com.vegdev.vegacademy.presenter.recipes.details.adapter.ingredients

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.model.data.models.Ingredient
import kotlinx.android.synthetic.main.recipe_details_ingredient.view.*

class DetailsIngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindIngredient(ingredient: Ingredient) {
        itemView.info_ingredient.text = ingredient.ingredient
        itemView.info_amount.text = ingredient.amount
    }
}