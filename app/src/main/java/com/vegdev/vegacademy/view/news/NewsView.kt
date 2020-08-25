package com.vegdev.vegacademy.view.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.presenter.news.NewsPresenter
import com.vegdev.vegacademy.presenter.news.pager.NewsPagerAdapter
import com.vegdev.vegacademy.view.main.IMainView
import kotlinx.android.synthetic.main.fragment_news.*

class NewsView : Fragment(), INewsView {

    private var presenter: NewsPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news_viewPager.adapter = NewsPagerAdapter(childFragmentManager)
        news_tablayout.setupWithViewPager(news_viewPager)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainView) presenter = NewsPresenter(context, this, context)
    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
    }

    override fun hideLayout() {
        news_viewPager.visibility = View.INVISIBLE
    }

    override fun showLayout() {
        news_viewPager.visibility = View.VISIBLE
    }


}

