package com.vegdev.vegacademy.ui.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Category
import kotlinx.android.synthetic.main.fragment_learning.*

class LearningFragment : Fragment() {

    val VIDEOS_CATEGORY = "videos"
    val ARTICLES_CATEGORY = "articles"

    val VEGACADEMY_COLLECTION = "veg"
    val VEGACADEMY_IMAGE = R.drawable.imageapp
    val VEGACADEMY_INSTAGRAM = "vegacademy"
    val VEGACADEMY_TITLE = "Academia Veg Introducción"

    val CEVA_COLLECTION = "ceva"
    val CEVA_IMAGE = R.drawable.image_ceva
    val CEVA_INSTAGRAM = "ceva.world"
    val CEVA_TITLE = "Abogacía Vegana Efectiva"

    val CARNISM_COLLECTION = "carn"
    val CARNISM_IMAGE = R.drawable.image_carnism
    val CARNISM_INSTAGRAM = "beyondcarnism"
    val CARNISM_TITLE = "Más Allá del Carnismo"


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

                navigateToVideoFragment(position, findNavController())

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

    private fun navigateToVideoFragment(position: Int, navController: NavController) {
        val category = Category(
            VIDEOS_CATEGORY,
            "",
            0,
            "",
            ""
        )

        when (position) {
            0 -> {
                category.categoryCollection = VEGACADEMY_COLLECTION
                category.categoryImage = VEGACADEMY_IMAGE
                category.categoryInstagram = VEGACADEMY_INSTAGRAM
                category.categoryTitle = VEGACADEMY_TITLE
                navController
                    .navigate(LearningFragmentDirections.actionVideo(category))
            }
            1 -> {
                category.categoryCollection = CEVA_COLLECTION
                category.categoryImage = CEVA_IMAGE
                category.categoryInstagram = CEVA_INSTAGRAM
                category.categoryTitle = CEVA_TITLE
                navController
                    .navigate(LearningFragmentDirections.actionVideo(category))
            }
            2 -> {
                category.categoryCollection = CARNISM_COLLECTION
                category.categoryImage = CARNISM_IMAGE
                category.categoryInstagram = CARNISM_INSTAGRAM
                category.categoryTitle = CARNISM_TITLE
                navController
                    .navigate(LearningFragmentDirections.actionVideo(category))
            }
        }
    }
}