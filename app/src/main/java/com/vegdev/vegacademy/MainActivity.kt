package com.vegdev.vegacademy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionManager
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.login.StartActivity
import com.vegdev.vegacademy.login.WelcomeActivity
import com.vegdev.vegacademy.models.Filter
import com.vegdev.vegacademy.models.LearningElement
import com.vegdev.vegacademy.models.Recipe
import com.vegdev.vegacademy.models.User
import com.vegdev.vegacademy.ui.learning.LearningCategoryFragment
import com.vegdev.vegacademy.ui.learning.LearningCategoryFragmentDirections
import com.vegdev.vegacademy.ui.learning.LearningFragment
import com.vegdev.vegacademy.ui.learning.LearningFragmentDirections
import com.vegdev.vegacademy.ui.news.NewsFragment
import com.vegdev.vegacademy.ui.news.NewsFragmentDirections
import com.vegdev.vegacademy.ui.recipes.*
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IYoutubePlayer, ILayoutManager, IRecipeManager,
    IUserManager {

    private val layoutUtils = LayoutUtils()
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    private var youtubePlayer: YouTubePlayer? = null
    private var isYoutubePlayerOpen: Boolean = false
    private var isYoutubeInitialized: Boolean = false
    private var currentLink: String = ""
    private var incomingLink: String = ""
    private var youTubePlayerHeight = 0
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        youTubePlayerHeight = resources.displayMetrics.widthPixels * 9 / 16


        main_toolbar.visibility = View.VISIBLE

        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        firestore.collection("users").document(firebaseAuth.currentUser!!.uid).get()
            .addOnSuccessListener {
                currentUser = it.toObject(User::class.java)
                nav_view.visibility = View.VISIBLE
                val newsFragment = getCurrentFragment() as NewsFragment
                newsFragment.makeNewsFragmentVisible()
                finishedLoading()
            }

        setSupportActionBar(main_toolbar)
        supportActionBar?.title = ""

        val navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)
        nav_view.setOnNavigationItemReselectedListener {}


        // set FAB click listener allowing to close youtube player interface
        fab_closeyoutube.hide()
        fab_closeyoutube.setOnClickListener {
            isYoutubePlayerOpen = false
            TransitionManager.beginDelayedTransition(container)
            main_player.visibility = View.INVISIBLE
            main_toolbar.visibility = View.VISIBLE
            fab_closeyoutube.hide()
            player_background.minHeight = 0
            youtubePlayer?.pause()
        }

        // search recipes toolbar logic
        edtxt_recipes_search.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val text = textView.editableText.toString()
                if (text.length >= 3) {
                    this.updateFilters(text)
                } else {
                    layoutUtils.createToast(this, "Debes ingresar por lo menos 3 caracteres")
                }
            }
            true
        }

        btn_recipe_search.setOnClickListener {
            val text = edtxt_recipes_search.editableText.toString()
            if (text.length >= 3) {
                this.updateFilters(text)
            } else {
                layoutUtils.createToast(this, "Debes ingresar por lo menos 3 caracteres")
            }
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
                    is NewsFragment -> NewsFragmentDirections.actionNavigationNewsToNavigationDonations()
                    is LearningFragment -> LearningFragmentDirections.actionNavigationLearningToNavigationDonations()
                    is LearningCategoryFragment -> LearningCategoryFragmentDirections.actionNavigationVideosToNavigationDonations()
                    is RecipesFragment -> RecipesFragmentDirections.actionNavigationRecipesToNavigationDonations()
                    is RecipeInfoFragment -> RecipeInfoFragmentDirections.actionNavigationRecipeInfoToNavigationDonations()
                    else -> NewsFragmentDirections.actionNavigationNewsToNavigationDonations()
                }
                nav_host_fragment.findNavController().navigate(navDirections)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCurrentFragment() =
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment)?.childFragmentManager?.fragments?.get(
            0
        )


    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()

        if (currentFragment is NewsFragment) {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("EXIT", true)
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }

    override fun openYoutubePlayer(learningElement: LearningElement) {
        if (fab_closeyoutube.isOrWillBeHidden) {
            fab_closeyoutube.show()
            main_player.visibility = View.VISIBLE
        }
        incomingLink = learningElement.link
        if (youtubePlayer == null) {
            initializeYoutube()
        } else {
            if (isYoutubePlayerOpen) {
                if (currentLink != incomingLink) {
                    youtubePlayer?.loadVideo(incomingLink)
                    currentLink = incomingLink
                } else {
                    layoutUtils.createToast(applicationContext, "Ya estás reproduciendo este video")
                }
            } else {
                main_toolbar.visibility = View.INVISIBLE
                player_background.minHeight = youTubePlayerHeight
                youtubePlayer?.loadVideo(incomingLink)
                currentLink = incomingLink
            }
        }
        isYoutubePlayerOpen = true
    }

    private fun initializeYoutube() {
        if (!isYoutubeInitialized) {
            isYoutubeInitialized = true

            player_background.minHeight = youTubePlayerHeight

            val youtubePlayerSupportFragment = YouTubePlayerSupportFragmentX.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.main_player, youtubePlayerSupportFragment).commit()
            youtubePlayerSupportFragment.initialize(
                resources.getString(R.string.API_KEY),
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(
                        p0: YouTubePlayer.Provider?,
                        p1: YouTubePlayer?,
                        p2: Boolean
                    ) {
                        p1?.loadVideo(incomingLink)
                        currentLink = incomingLink
                        youtubePlayer = p1
                    }

                    override fun onInitializationFailure(
                        p0: YouTubePlayer.Provider?,
                        p1: YouTubeInitializationResult?
                    ) {
                        layoutUtils.createToast(
                            applicationContext,
                            "Error al iniciar Youtube"
                        )
                    }
                })
        }
    }

    override fun currentlyLoading() {
        main_progressbar.visibility = View.VISIBLE
    }

    override fun finishedLoading() {
        main_progressbar.visibility = View.INVISIBLE
    }

    override fun isNavViewVisible(): Boolean {
        return nav_view.visibility == View.VISIBLE
    }

    override fun toolbarOff() {
        main_toolbar.visibility = View.INVISIBLE
    }

    override fun toolbarOn() {
        main_toolbar.visibility = View.VISIBLE
    }

    override fun searchRecipesOn() {
        recipes_search.visibility = View.VISIBLE
    }

    override fun searchRecipesOff() {
        recipes_search.visibility = View.GONE
    }

    override fun suggestRecipe(recipe: Recipe) {

        val docRef = FirebaseFirestore.getInstance().collection("recSug").document()
        recipe.id = docRef.id
        docRef.set(recipe)
            .addOnSuccessListener { layoutUtils.createToast(this, "Receta enviada") }
            .addOnFailureListener { layoutUtils.createToast(this, "Error al enviar receta") }
    }

    override fun updateFilters(byTitle: String) {
        val recipesFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.fragments?.get(
                0
            ) as RecipesFragment
        recipesFragment.fetchFilteredRecipes(byTitle)
    }

    override fun updateFilters(byTaste: String, byMeal: String) {
        val recipesFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.fragments?.get(
                0
            ) as RecipesFragment
        recipesFragment.fetchFilteredRecipes(byTaste, byMeal)
    }

    override fun getFiltersList(): MutableList<Filter?> {
        val recipesFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.fragments?.get(
                0
            ) as RecipesFragment
        return recipesFragment.getFiltersList()
    }

    override fun getCurrentUser(): User? = currentUser


}


interface IYoutubePlayer {
    fun openYoutubePlayer(learningElement: LearningElement)
}

interface ILayoutManager {
    fun toolbarOff()
    fun toolbarOn()
    fun searchRecipesOn()
    fun searchRecipesOff()
    fun currentlyLoading()
    fun finishedLoading()
    fun isNavViewVisible(): Boolean
}

interface IRecipeManager {
    fun suggestRecipe(recipe: Recipe)
    fun updateFilters(byTitle: String)
    fun updateFilters(byTaste: String, byMeal: String)
    fun getFiltersList(): MutableList<Filter?>
}

interface IUserManager {
    fun getCurrentUser(): User?
}