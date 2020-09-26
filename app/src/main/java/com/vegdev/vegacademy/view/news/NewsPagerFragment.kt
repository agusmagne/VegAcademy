package com.vegdev.vegacademy.view.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.contract.news.NewsContract
import com.vegdev.vegacademy.presenter.news.pager.NewsPagerPresenter
import com.vegdev.vegacademy.presenter.news.pager.NewsViewHolder
import kotlinx.android.synthetic.main.news_page.*
import kotlinx.coroutines.launch

class NewsPagerFragment(private val position: Int) : Fragment(), NewsContract.PagerView {

    private var presenter: NewsPagerPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch { presenter?.fetchNewsAndBuildRecyclerViews(position) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainContract.View) presenter =
            NewsPagerPresenter((parentFragment as NewsContract.View), this, context)
    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
    }


    override fun buildRv(adapter: RecyclerView.Adapter<NewsViewHolder>) {
        news_viewpager_rv?.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }
}