package com.vegdev.vegacademy.view.learning.categories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.domain.interactor.learning.CategoriesInteractor
import com.vegdev.vegacademy.presenter.learning.categories.CategoriesPresenter
import com.vegdev.vegacademy.presenter.learning.categories.CategoryViewHolder
import com.vegdev.vegacademy.view.main.IMainView
import kotlinx.android.synthetic.main.learning_view.*
import kotlinx.coroutines.launch

class CategoriesView : Fragment(),
    ICategoriesView {

    private var presenter: CategoriesPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.learning_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            presenter?.fetchAndBuildRecyclerViews()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainView) this.presenter =
            CategoriesPresenter(
                context,
                this,
                context,
                findNavController(),
                CategoriesInteractor()
            )
    }

    override fun onDetach() {
        super.onDetach()
        this.presenter = null
    }

    override fun hideLayout() {
        fragment_learning_cl.visibility = View.INVISIBLE
    }

    override fun showLayout() {
        fragment_learning_cl.visibility = View.VISIBLE
    }

    override fun buildVideosRV(adapter: RecyclerView.Adapter<CategoryViewHolder>) {
        videos_rv.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            this.adapter = adapter
        }
    }

    override fun buildArticlesRV(adapter: RecyclerView.Adapter<CategoryViewHolder>) {
        articles_rv.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            this.adapter = adapter
        }
    }
}