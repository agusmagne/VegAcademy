package com.vegdev.vegacademy.view.recipes.details

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.details.adapter.ingredients.DetailsIngredientsAdapter
import com.vegdev.vegacademy.presenter.recipes.details.adapter.steps.DetailsStepsAdapter
import com.vegdev.vegacademy.presenter.recipes.details.details.RecipeDetailsPresenter
import com.vegdev.vegacademy.view.main.main.MainView
import kotlinx.android.synthetic.main.recipe_details.*

class RecipeDetailsFragment : Fragment(), RecipeDetailsView {

    private var presenter: RecipeDetailsPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: RecipeDetailsFragmentArgs by navArgs()
        presenter?.buildRecyclerViewsAndBindRecipeInfo(args.recipe, args.src)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainView) presenter = RecipeDetailsPresenter(this, context)
    }

    override fun setIngredientsRecyclerViewAdapter(adapter: DetailsIngredientsAdapter) {
        details_ing_rv.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    override fun setStepsRecyclerViewAdapter(adapter: DetailsStepsAdapter) {
        details_steps_rv.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    override fun bindRecipe(recipe: SingleRecipe, src: Bitmap) {
        Glide.with(requireContext()).load(src).into(this.src)
        recipe_desc.text = recipe.desc
        title.text = recipe.title
    }


}