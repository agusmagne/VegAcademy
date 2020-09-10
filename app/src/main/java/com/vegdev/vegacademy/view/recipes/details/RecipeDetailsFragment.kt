package com.vegdev.vegacademy.view.recipes.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.info.info.RecipesInfoPresenter
import com.vegdev.vegacademy.presenter.recipes.info.ingredientsAdapter.InfoIngredientsAdapter
import com.vegdev.vegacademy.presenter.recipes.info.stepsAdater.InfoStepsAdapter
import com.vegdev.vegacademy.view.main.main.MainView
import kotlinx.android.synthetic.main.fragment_recipe_info.*

class RecipeDetailsFragment : Fragment(), RecipeDetailsView {

    private var presenter: RecipesInfoPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: RecipeDetailsFragmentArgs by navArgs()
        presenter?.buildRecyclerViewsAndBindRecipeInfo(args.recipe)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainView) presenter = RecipesInfoPresenter(this, context)
    }

    override fun setIngredientsRecyclerViewAdapter(adapter: InfoIngredientsAdapter) {
        info_ingredients_rv.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    override fun setStepsRecyclerViewAdapter(adapter: InfoStepsAdapter) {
        info_steps_rv.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    override fun bindRecipe(recipe: SingleRecipe) {
        FirebaseStorage.getInstance().getReference("recipes/recipes/")
            .child(recipe.id).downloadUrl.addOnSuccessListener {
                Glide.with(this).load(it).into(src)
            }
        recipe_desc.text = recipe.desc
        title.text = recipe.title
    }


}