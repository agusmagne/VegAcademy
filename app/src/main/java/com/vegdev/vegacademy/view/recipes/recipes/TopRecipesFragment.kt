package com.vegdev.vegacademy.view.recipes.recipes

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.TopRecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.toprecipes.adapter.ParentRecipesAdapter
import com.vegdev.vegacademy.presenter.recipes.toprecipes.toprecipes.TopRecipesPresenter
import com.vegdev.vegacademy.view.main.main.MainView
import kotlinx.android.synthetic.main.fragment_top_recipes.*
import kotlinx.coroutines.launch

class TopRecipesFragment : Fragment(), TopRecipesView {

    private var presenter: TopRecipesPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_top_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            presenter?.buildRVs()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_addrecipe).isVisible = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainView) presenter =
            TopRecipesPresenter(context, this, this, context, TopRecipesInteractor())
    }

    override fun buildRecipesParentRV(adapter: ParentRecipesAdapter) {
        parent_recipes_rv.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }
}