package com.vegdev.vegacademy.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionManager
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.login.StartActivity
import com.vegdev.vegacademy.login.WelcomeActivity
import com.vegdev.vegacademy.model.data.models.Filter
import com.vegdev.vegacademy.model.data.models.User
import com.vegdev.vegacademy.model.domain.interactor.main.MainInteractor
import com.vegdev.vegacademy.presenter.main.MainPresenter
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.learning.categories.CategoriesView
import com.vegdev.vegacademy.view.learning.categories.CategoriesViewDirections
import com.vegdev.vegacademy.view.learning.elements.ElementsView
import com.vegdev.vegacademy.view.learning.elements.ElementsViewDirections
import com.vegdev.vegacademy.view.news.news.NewsView
import com.vegdev.vegacademy.view.news.news.NewsViewDirections
import com.vegdev.vegacademy.view.recipes.RecipeDialogAddFragment
import com.vegdev.vegacademy.view.recipes.RecipeDialogFilterFragment
import com.vegdev.vegacademy.view.recipes.RecipeInfoFragment
import com.vegdev.vegacademy.view.recipes.RecipeInfoFragmentDirections
import com.vegdev.vegacademy.view.recipes.recipes.RecipesView
import com.vegdev.vegacademy.view.recipes.recipes.RecipesViewDirections
import kotlinx.android.synthetic.main.activity_main.*

class MainView : AppCompatActivity(), IMainView {

    private val layoutUtils = LayoutUtils()
    lateinit var firebaseAuth: FirebaseAuth
    private var userInfo: User? = null

    private var presenter = MainPresenter(this, supportFragmentManager, this, MainInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        val navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)
        nav_view.setOnNavigationItemReselectedListener {}

        presenter.init()


        setSupportActionBar(main_toolbar)
        supportActionBar?.title = ""


        // set FAB click listener allowing to close youtube player interface
        fab_closeyoutube.hide()
        fab_closeyoutube.setOnClickListener {
            presenter.closeYouTubePlayer()
        }

        // search recipes toolbar logic
        edtxt_recipes_search.setOnEditorActionListener { textView, actionId, keyEvent ->
            this.updateFilters(textView.editableText.toString(), actionId)
            false
        }

        btn_recipe_search.setOnClickListener {
            this.updateFilters(edtxt_recipes_search.editableText.toString(), 3)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        menu?.findItem(R.id.action_addrecipe)?.isVisible = false
        menu?.findItem(R.id.action_filterrecipe)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_logout -> {
                firebaseAuth.signOut()
                val intent = Intent(this, StartActivity::class.java)
                this.startActivity(intent)
                layoutUtils.overrideEnterAndExitTransitions(this)
            }

            R.id.action_addrecipe -> {
                RecipeDialogAddFragment().show(supportFragmentManager, "")
            }

            R.id.action_filterrecipe -> {
                RecipeDialogFilterFragment().show(supportFragmentManager, "")
            }

            R.id.action_donate -> {
                val currentFragment = getCurrentFragment()
                val navDirections: NavDirections
                navDirections = when (currentFragment) {
                    is NewsView -> NewsViewDirections.actionNavigationNewsToNavigationDonations()
                    is CategoriesView -> CategoriesViewDirections.actionNavigationLearningToNavigationDonations()
                    is ElementsView -> ElementsViewDirections.actionNavigationVideosToNavigationDonations()
                    is RecipesView -> RecipesViewDirections.actionNavigationRecipesToNavigationDonations()
                    is RecipeInfoFragment -> RecipeInfoFragmentDirections.actionNavigationRecipeInfoToNavigationDonations()
                    else -> NewsViewDirections.actionNavigationNewsToNavigationDonations()
                }
                nav_host_fragment.findNavController().navigate(navDirections)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getCurrentFragment(): Fragment? =
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment)?.childFragmentManager?.fragments?.get(
            0
        )

    override fun hideLayout() {}
    override fun showLayout() {}

    override fun navigateToDirection(direction: NavDirections) {
        findNavController(R.id.nav_host_fragment).navigate(direction)
    }

    override fun getUserInfo(): User? = userInfo
    override fun setUserInfo(userInfo: User?) {
        this.userInfo = userInfo
    }

    override fun onFilterByTitleUpdate() {
        TODO("Not yet implemented")
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()

        if (currentFragment is NewsView) {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("EXIT", true)
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }

    override fun onVideoClicked(url: String) {
        presenter.playVideo(url, fab_closeyoutube.isOrWillBeShown)
    }

    override fun showFAB() {
        fab_closeyoutube.show()
    }

    override fun hideFAB() {
        fab_closeyoutube.hide()
    }

    override fun transitionBackgroundToHeight(minHeight: Int) {
        TransitionManager.beginDelayedTransition(container)
        player_background.minHeight = minHeight
    }

    override fun showPlayer() {
        player.visibility = View.VISIBLE
    }

    override fun hidePlayer() {
        player.visibility = View.INVISIBLE
    }

    override fun showNavView() {
        nav_view.visibility = View.VISIBLE
    }

    override fun hideNavView() {
        nav_view.visibility = View.INVISIBLE
    }

//    override fun suggestRecipe(recipe: Recipe) {
//
//        val docRef = FirebaseFirestore.getInstance().collection("recSug").document()
//        recipe.id = docRef.id
//        docRef.set(recipe)
//            .addOnSuccessListener { layoutUtils.createToast(this, "Receta enviada") }
//            .addOnFailureListener { layoutUtils.createToast(this, "Error al enviar receta") }
//    }

    override fun updateFilters(newTitle: String, actionId: Int) {
        val recipesFragment = getCurrentFragment() as RecipesView
        recipesFragment.updateFilters(newTitle, actionId)
    }

    override fun updateFilters(taste: String, meal: String) {
        val recipesFragment = getCurrentFragment() as RecipesView
        recipesFragment.updateFilters(taste, meal)
    }

    override fun getRecipeFilters(): MutableList<Filter> {
        val recipesFragment = getCurrentFragment() as RecipesView
        return recipesFragment.getFilters()
    }

    override fun showRecipesSearchBar() {
        recipes_search.visibility = View.VISIBLE
    }

    override fun hideRecipesSearchBar() {
        recipes_search.visibility = View.INVISIBLE
    }

    override fun showProgress() {
        main_progressbar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        main_progressbar.visibility = View.INVISIBLE
    }

    override fun showToolbar() {
        main_toolbar.visibility = View.VISIBLE
    }

    override fun hideToolbar() {
        main_toolbar.visibility = View.INVISIBLE
    }
}