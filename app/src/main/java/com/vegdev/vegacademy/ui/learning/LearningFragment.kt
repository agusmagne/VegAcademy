package com.vegdev.vegacademy.ui.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Category
import kotlinx.android.synthetic.main.fragment_learning.*

class LearningFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_learning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        videos_rv.apply {
            val videosCategories = resources.obtainTypedArray(R.array.videos_categories_drawables)
            val videosTitles = resources.obtainTypedArray(R.array.videos_categories_titles)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = LearningRvAdapter(videosCategories, videosTitles) { position ->

                when (position) {
                    0 -> {
                        val category = Category("veg", R.drawable.imageapp, "@ceva.world")
                        this.findNavController()
                                .navigate(LearningFragmentDirections.actionVideo(category))
                    }
                    1 -> {
                        val category = Category("ceva", R.drawable.image_ceva, "@ceva.world")
                        this.findNavController()
                                .navigate(LearningFragmentDirections.actionVideo(category))
                    }
                    2 -> {
                        val category = Category("carn", R.drawable.image_carnism, "@beyondcarnism")
                        this.findNavController()
                                .navigate(LearningFragmentDirections.actionVideo(category))
                    }
                }
            }
        }



        articles_rv.apply {
            val articlesCategories =
                    resources.obtainTypedArray(R.array.articles_categories_drawables)
            val articlesTitles = resources.obtainTypedArray(R.array.articles_categories_titles)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = LearningRvAdapter(articlesCategories, articlesTitles) {

            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}