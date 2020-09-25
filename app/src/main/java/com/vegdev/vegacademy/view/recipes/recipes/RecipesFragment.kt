package com.vegdev.vegacademy.view.recipes.recipes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.recipes.RecipesContract
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.RecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.recipes.parent.adapter.ParentRecipesAdapter
import com.vegdev.vegacademy.presenter.recipes.recipes.parent.adapter.ScrollStateHolder
import com.vegdev.vegacademy.presenter.recipes.recipes.recipes.RecipesPresenter
import com.vegdev.vegacademy.view.main.main.MainView
import kotlinx.android.synthetic.main.recipes.*
import kotlinx.coroutines.launch

class RecipesFragment : Fragment(), RecipesView, RecipesContract.View {

    private var presenter: RecipesPresenter? = null
    private lateinit var scrollStateHolder: ScrollStateHolder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        scrollStateHolder = ScrollStateHolder(savedInstanceState)
        return inflater.inflate(R.layout.recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (presenter?.parentAdapter != null) {
            postponeEnterTransition()
            parent_recipes_rv.doOnPreDraw { startPostponedEnterTransition() }
        }
        lifecycleScope.launch {
            presenter?.buildRVs(scrollStateHolder)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        scrollStateHolder.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_addrecipe).isVisible = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainView) presenter =
            RecipesPresenter(this, context, RecipesInteractor())
    }

    override fun buildRecipesParentRV(adapter: ParentRecipesAdapter) {
        parent_recipes_rv?.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    override fun openRecipeDetails(recipe: SingleRecipe, bitmap: Bitmap?, view: View) {
        presenter?.openRecipeDetails(recipe, bitmap, view)
    }

    override fun makeToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}