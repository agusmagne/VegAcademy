package com.vegdev.vegacademy.presenter.learning.categories

import android.os.Bundle
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.learning.CategoriesContract
import com.vegdev.vegacademy.model.data.models.Category
import com.vegdev.vegacademy.model.domain.interactor.learning.CategoriesInteractor

class CategoriesPresenter(
    private val iView: CategoriesContract.View,
    private val iMainView: MainView
) : CategoriesContract.Actions {

    var videosAdapter: CategoriesAdapter? = null
    private var articlesAdapter: CategoriesAdapter? = null
    private val interactor = CategoriesInteractor()

    override suspend fun fetchAndBuildRecyclerViews() {
        iMainView.showProgress()

        if (videosAdapter == null) {
            val videoCategories = interactor.getVideoCategories()
            videosAdapter = buildAdapter(videoCategories)
        }

        if (articlesAdapter == null) {
            val articlesCategories = interactor.getArticlesCategories()
            articlesAdapter = buildAdapter(articlesCategories)

        }
        iView.buildVideosRV(videosAdapter)
        iView.buildArticlesRV(articlesAdapter)
        iMainView.hideProgress()

    }

    private fun buildAdapter(categories: MutableList<Category>): CategoriesAdapter {
        return CategoriesAdapter(categories) { category, view ->
            val bundle = Bundle()
            bundle.putParcelable("category", category)
            val extras = FragmentNavigatorExtras(
                view[0] to (category.title + "src")
            )
            iMainView.navigateWithOptions(R.id.navigation_element, bundle, null, extras)
        }
    }

}