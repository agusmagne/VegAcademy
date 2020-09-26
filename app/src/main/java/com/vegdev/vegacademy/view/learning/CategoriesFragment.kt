package com.vegdev.vegacademy.view.learning

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.learning.CategoriesContract
import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.presenter.learning.categories.CategoriesPresenter
import com.vegdev.vegacademy.presenter.learning.categories.CategoryViewHolder
import kotlinx.android.synthetic.main.categories.*
import kotlinx.coroutines.launch

class CategoriesFragment : Fragment(), CategoriesContract.View {

    private var presenter: CategoriesPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (presenter?.videosAdapter != null) {
            postponeEnterTransition()
            videos_rv.doOnPreDraw { startPostponedEnterTransition() }
        }
        lifecycleScope.launch {
            presenter?.fetchAndBuildRecyclerViews()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainContract.View) this.presenter =
            CategoriesPresenter(this, context)
    }

    override fun buildVideosRV(adapter: RecyclerView.Adapter<CategoryViewHolder>?) {
        videos_rv?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }

    }

    override fun buildArticlesRV(adapter: RecyclerView.Adapter<CategoryViewHolder>?) {
        articles_rv?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
    }
}