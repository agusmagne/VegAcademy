package com.vegdev.vegacademy.view.news.news

import android.content.Context
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.presenter.news.NewsPagerAdapter
import com.vegdev.vegacademy.presenter.news.NewsPresenter
import com.vegdev.vegacademy.view.main.main.MainView
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment(), NewsView {

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
        if (context is MainView) presenter = NewsPresenter(context, this, context)
    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
    }

    override fun hideLayout() {
        TransitionManager.beginDelayedTransition(fragment_news)
        news_viewPager.visibility = View.INVISIBLE
    }

    override fun showLayout() {
        TransitionManager.beginDelayedTransition(fragment_news)
        news_viewPager.visibility = View.VISIBLE
    }
}


