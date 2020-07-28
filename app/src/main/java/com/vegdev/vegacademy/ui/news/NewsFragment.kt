package com.vegdev.vegacademy.ui.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vegdev.vegacademy.IOnFragmentBackPressed
import com.vegdev.vegacademy.IYoutubePlayer
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.utils.ModelsUtils
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment(), IOnFragmentBackPressed {

    private val modelsUtils = ModelsUtils()
    private var youtubePlayer: IYoutubePlayer? = null

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

    override fun onFragmentBackPressed(): Boolean {
        if (youtubePlayer?.getYoutubePlayerState()!!) {
            youtubePlayer?.closeYoutubePlayer()
            return true
        } else {
            return false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IYoutubePlayer) {
            youtubePlayer = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        youtubePlayer = null
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

