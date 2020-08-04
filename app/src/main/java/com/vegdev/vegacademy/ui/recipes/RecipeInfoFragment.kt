package com.vegdev.vegacademy.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Recipe
import kotlinx.android.synthetic.main.fragment_recipe_info.*

class RecipeInfoFragment : Fragment() {

    private var recipe: Recipe? = null
    private val args: RecipeInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recipe = args.recipe
        return inflater.inflate(R.layout.fragment_recipe_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(recipe)
    }

    private fun bindView(recipe: Recipe?) {
        recipe?.let {
            Glide.with(this).load(recipe.src).into(src)
            title.text = recipe.title
            ingredients.text = recipe.ing?.joinToString()
        }
    }
}