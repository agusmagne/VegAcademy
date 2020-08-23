package com.vegdev.vegacademy.view.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vegdev.vegacademy.ILayoutManager
import com.vegdev.vegacademy.IYoutubePlayer
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.utils.ModelsUtils
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {

    private val modelsUtils = ModelsUtils()
    private var youtubePlayer: IYoutubePlayer? = null
    private var iLayoutManager: ILayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iLayoutManager?.currentlyLoading()
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news_viewPager.adapter = NewsPagerAdapter(childFragmentManager)
        news_tablayout.setupWithViewPager(news_viewPager)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IYoutubePlayer) {
            youtubePlayer = context
        }
        if (context is ILayoutManager) {
            iLayoutManager = context
        }

    }

    override fun onDetach() {
        super.onDetach()
        youtubePlayer = null
        iLayoutManager = null
    }

    fun makeViewPagerVisible() {
        news_viewPager.visibility = View.VISIBLE
    }

    fun makeNewsFragmentVisible() {
        news_viewPager.visibility = View.VISIBLE
    }


}

private class NewsPagerAdapter(childFragmentManager: FragmentManager) :
    FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) {
            "NUEVOS VIDEOS"
        } else {
            "NUEVOS ARTÍCULOS"
        }
    }

    override fun getItem(position: Int): Fragment = NewsPageFragment(position)
    override fun getCount(): Int = 2

}

