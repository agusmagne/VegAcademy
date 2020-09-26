package com.vegdev.vegacademy.view.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.news.NewsContract
import com.vegdev.vegacademy.presenter.news.NewsPagerAdapter
import kotlinx.android.synthetic.main.news.*

class NewsFragment : Fragment(), NewsContract.View {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news_viewPager.adapter = NewsPagerAdapter(childFragmentManager)
        news_tablayout.setupWithViewPager(news_viewPager)
    }

    override fun hideLayout() {
        news_viewPager.visibility = View.INVISIBLE
    }

    override fun showLayout() {
        news_viewPager.visibility = View.VISIBLE
    }
}


