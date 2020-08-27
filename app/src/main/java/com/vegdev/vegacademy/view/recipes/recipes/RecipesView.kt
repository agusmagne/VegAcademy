package com.vegdev.vegacademy.view.recipes.recipes

import android.content.Context
import android.os.Bundle
import android.transition.TransitionManager
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Filter
import com.vegdev.vegacademy.model.data.models.Recipe
import com.vegdev.vegacademy.model.domain.interactor.recipes.RecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.RecipesPresenter
import com.vegdev.vegacademy.view.main.IMainView
import com.vegdev.vegacademy.view.recipes.filters.FiltersAdapter
import com.vegdev.vegacademy.view.recipes.recipes.adapter.RecipesViewHolder
import kotlinx.android.synthetic.main.fragment_recipes.*

class RecipesView : Fragment(), IRecipesView {

    private var presenter: RecipesPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.buildRecipesRecyclerView()
        presenter?.buildFiltersRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_addrecipe).isVisible = true
        menu.findItem(R.id.action_filterrecipe).isVisible = true
    }


    override fun onStart() {
        super.onStart()
        (recipes_rv.adapter as FirestorePagingAdapter<*, *>).startListening()
        presenter?.showRecipesSearchBar()
    }

    override fun onStop() {
        super.onStop()
        (recipes_rv.adapter as FirestorePagingAdapter<*, *>).stopListening()
        presenter?.hideRecipesSearchBar()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainView) presenter =
            RecipesPresenter(context, context, this, RecipesInteractor())
    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
    }

    override fun buildFiltersRecyclerView(adapter: FirestorePagingAdapter<Recipe, RecipesViewHolder>) {
        recipes_rv.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    override fun buildFiltersRecyclerView(adapter: FiltersAdapter) {
        filters_rv.apply {
            this.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            this.adapter = adapter
        }
    }

    override fun updateFilters(newTitle: String, actionId: Int) {
        presenter?.updateFilters(newTitle, actionId)
    }

    override fun updateFilters(taste: String, meal: String) {
        presenter?.updateFilters(taste, meal)
    }

    override fun getFilters(): MutableList<Filter> = presenter?.interactor?.filters!!

    override fun hideLayout() {
        recipes_fragment.visibility = View.INVISIBLE
    }

    override fun showLayout() {
        TransitionManager.beginDelayedTransition(recipes_fragment)
        recipes_fragment.visibility = View.VISIBLE
    }


}