package com.vegdev.vegacademy.presenter.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vegdev.vegacademy.view.news.NewsPagerView

class NewsPagerAdapter(childFragmentManager: FragmentManager) :
    FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) {
            "NUEVOS VIDEOS"
        } else {
            "NUEVOS ARTÍCULOS"
        }
    }

    override fun getItem(position: Int): Fragment = NewsPagerView(position)
    override fun getCount(): Int = 2

}
