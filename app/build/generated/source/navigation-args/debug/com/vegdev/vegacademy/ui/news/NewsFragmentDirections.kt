package com.vegdev.vegacademy.ui.news

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.vegdev.vegacademy.R

class NewsFragmentDirections private constructor() {
  companion object {
    fun actionNavigationNewsToNavigationRecipes(): NavDirections =
        ActionOnlyNavDirections(R.id.action_navigation_news_to_navigation_recipes)

    fun actionNavigationNewsToNavigationLearning(): NavDirections =
        ActionOnlyNavDirections(R.id.action_navigation_news_to_navigation_learning)
  }
}
