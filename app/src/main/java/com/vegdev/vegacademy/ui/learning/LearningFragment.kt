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
import com.vegdev.vegacademy.utils.ModelsUtils
import kotlinx.android.synthetic.main.fragment_learning.*

class LearningFragment : Fragment() {

    val modelsUtils = ModelsUtils()

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
                navigateToFragmentAsVideo(position, findNavController())
            }
        }
        articles_rv.apply {
            val articlesCategories =
                resources.obtainTypedArray(R.array.articles_categories_drawables)
            val articlesTitles = resources.obtainTypedArray(R.array.articles_categories_titles)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = LearningRvAdapter(articlesCategories, articlesTitles) { position ->
                navigateToFragmentAsArticle(position, findNavController())
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun navigateToFragmentAsVideo(position: Int, navController: NavController) {
        when (position) {
            0 -> {
                val category = modelsUtils.createVegAcademyCategory()
                navController
                    .navigate(LearningFragmentDirections.actionVideo(category))
            }
            1 -> {
                val category = modelsUtils.createCevaCategory()
                navController
                    .navigate(LearningFragmentDirections.actionVideo(category))
            }
            2 -> {
                val category = modelsUtils.createCarnismCategory()
                navController
                    .navigate(LearningFragmentDirections.actionVideo(category))
            }
        }
    }

    private fun navigateToFragmentAsArticle(position: Int, navController: NavController) {
        when (position) {
            0 -> {
                val category = modelsUtils.createComunicationCategory()
                navController.navigate(LearningFragmentDirections.actionVideo(category))
            }
            1 -> {
                val category = modelsUtils.createOtherMovementsCategory()
                navController.navigate(LearningFragmentDirections.actionVideo(category))
            }
            2 -> {
                val category = modelsUtils.createNutritionCategory()
                navController.navigate(LearningFragmentDirections.actionVideo(category))
            }
        }
    }
}