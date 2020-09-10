package com.vegdev.vegacademy.presenter.recipes.info.ingredientsAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.model.data.models.Ingredient
import kotlinx.android.synthetic.main.single_info_ingredient.view.*

class InfoIngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindIngredient(ingredient: Ingredient) {
        itemView.info_ingredient.text = ingredient.ingredient
        itemView.info_amount.text = ingredient.amount
    }
}