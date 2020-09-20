package com.vegdev.vegacademy.view.recipes.recipes

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.RecipesContract
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.RecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent.ParentRecipesAdapter
import com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent.ScrollStateHolder
import com.vegdev.vegacademy.presenter.recipes.toprecipes.RecipesPresenter
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
            RecipesPresenter(context, this, this, context, RecipesInteractor())
    }

    override fun buildRecipesParentRV(adapter: ParentRecipesAdapter) {
        parent_recipes_rv?.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    override fun openRecipeDetails(recipe: SingleRecipe, drawable: Drawable, view: View) {
        presenter?.openRecipeDetails(recipe, drawable, view)
    }

    override fun getSelectedRecipeDrawable(): Drawable? = presenter?.getCurrentRecipeDrawable()
}