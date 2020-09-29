package com.vegdev.vegacademy.view.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.news.NewsContract
import com.vegdev.vegacademy.presenter.news.NewsPagerAdapter
import kotlinx.android.synthetic.main.news.*

class NewsFragment : Fragment(), NewsContract.View {

    private var viewPager: ViewPager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.news, container, false)
        viewPager = root.findViewById(R.id.news_viewPager)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news_viewPager.adapter = NewsPagerAdapter(childFragmentManager)
        news_tablayout.setupWithViewPager(news_viewPager)
    }

    override fun hideLayout() {
        viewPager?.visibility = View.INVISIBLE
    }

    override fun showLayout() {
        viewPager?.visibility = View.VISIBLE
    }
}


