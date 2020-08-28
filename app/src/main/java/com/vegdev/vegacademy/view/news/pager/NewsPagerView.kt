package com.vegdev.vegacademy.view.news.pager

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
import com.vegdev.vegacademy.model.domain.interactor.news.NewsPagerInteractor
import com.vegdev.vegacademy.presenter.news.pager.NewsPagerPresenter
import com.vegdev.vegacademy.presenter.news.pager.NewsViewHolder
import com.vegdev.vegacademy.view.main.main.MainView
import com.vegdev.vegacademy.view.news.news.NewsView
import kotlinx.android.synthetic.main.fragment_news_page.*
import kotlinx.coroutines.launch

class NewsPagerView(private val position: Int) : Fragment(), INewsPageView {

    private var presenter: NewsPagerPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch { presenter?.fetchNewsAndBuildRecyclerViews(position) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainView) presenter =
            NewsPagerPresenter(
                context,
                this,
                context,
                NewsPagerInteractor()
            )
    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
    }


    override fun buildRv(adapter: RecyclerView.Adapter<NewsViewHolder>) {
        news_viewpager_rv.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    override fun hideLayout() {
        (parentFragment as NewsView).hideLayout()
    }

    override fun showLayout() {
        (parentFragment as NewsView).showLayout()
    }
}