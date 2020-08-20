package com.vegdev.vegacademy.ui.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Filter
import com.vegdev.vegacademy.models.Recipe
import kotlinx.android.synthetic.main.fragment_recipes_element.view.*
import kotlinx.android.synthetic.main.recipe_single_filter.view.*
import java.util.*

class RecipesFragmentAdapters {


    fun fetchRecipes(
        firestore: FirebaseFirestore,
        likedRecipesId: MutableList<String>,
        uid: String,
        onRecipeClick: (Recipe) -> Unit
    ): FirestorePagingAdapter<Recipe, RecipeViewHolder> {
        val query = firestore.collection("rec")
        val config =
            PagedList.Config.Builder().setInitialLoadSizeHint(10).setPageSize(3).build()
        val response =
            FirestorePagingOptions.Builder<Recipe>().setQuery(query, config, Recipe::class.java)
                .build()

        return object : FirestorePagingAdapter<Recipe, RecipeViewHolder>(response) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecipeViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_recipes_element, parent, false)
                return RecipeViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holder: RecipeViewHolder,
                position: Int,
                recipe: Recipe
            ) {
                val recipeId = recipe.id

                holder.bindRecipe(recipe)
                if (likedRecipesId.contains(recipeId)) {
                    holder.recipeLiked()
                }

                holder.itemView.src.setOnClickListener { onRecipeClick(recipe) }
                holder.itemView.likes_btn.setOnClickListener { view ->
                    view.isClickable = false
                    holder.itemView.likes_progressbar.visibility = View.VISIBLE
                    if (!likedRecipesId.contains(recipeId)) {
                        firestore.collection("rec").document(recipeId)
                            .update("likes", FieldValue.increment(1))
                        firestore.collection("users").document(uid)
                            .update("likedRecipesId", FieldValue.arrayUnion(recipeId))
                            .addOnSuccessListener {
                                likedRecipesId.add(recipeId)
                                holder.recipeLiked()
                                holder.itemView.likes.text =
                                    (holder.itemView.likes.text.toString().toInt() + 1).toString()
                                view.isClickable = true
                                holder.itemView.likes_progressbar.visibility = View.INVISIBLE
                            }
                    } else {
                        firestore.collection("rec").document(recipeId)
                            .update("likes", FieldValue.increment(-1))
                        firestore.collection("users").document(uid)
                            .update("likedRecipesId", FieldValue.arrayRemove(recipeId))
                            .addOnSuccessListener {
                                likedRecipesId.remove(recipeId)
                                holder.recipeDisiked()
                                view.isClickable = true
                                holder.itemView.likes.text =
                                    (holder.itemView.likes.text.toString().toInt() - 1).toString()
                                holder.itemView.likes_progressbar.visibility = View.INVISIBLE
                            }
                    }

                }

            }

//            override fun onLoadingStateChanged(state: LoadingState) {
//                super.onLoadingStateChanged(state)
//                when (state) {
//                    LoadingState.ERROR -> {
//                    }
//                    LoadingState.FINISHED -> {
//                        pagingProgressbar.visibility = View.INVISIBLE
//                    }
//                    LoadingState.LOADED -> {
//                        pagingProgressbar.visibility = View.INVISIBLE
//                    }
//                    LoadingState.LOADING_INITIAL -> {
//                    }
//                    LoadingState.LOADING_MORE -> {
//                        pagingProgressbar.visibility = View.VISIBLE
//                    }
//                }
//            }
        }
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindRecipe(recipe: Recipe) {
            Glide.with(itemView.context).load(recipe.src).into(itemView.src)
            val title = recipe.title
            val titleUppercase =
                title.substring(0, 1).toUpperCase(Locale.ROOT) + title.substring(1, title.length)
            itemView.title.text = titleUppercase
            itemView.likes.text = recipe.likes.toString()
        }


        fun recipeLiked() {
            itemView.likes.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            itemView.ic_like.setColorFilter(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
        }

        fun recipeDisiked() {
            itemView.likes.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.superAccent
                )
            )
            itemView.ic_like.setColorFilter(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.superAccent
                )
            )
        }
    }

    class FiltersStaggeredRvAdapter(
        val filtersList: MutableList<Filter?>,
        val listener: (Filter) -> Unit
    ) :
        RecyclerView.Adapter<FiltersViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_single_filter, parent, false)
            return FiltersViewHolder(itemView)
        }

        override fun getItemCount(): Int = filtersList.size

        override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {
            filtersList[position]?.let { filter ->
                holder.bindFilter(filter)
                holder.itemView.setOnClickListener { listener(filter) }
            }
        }
    }

    class FiltersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindFilter(filter: Filter) {
            itemView.filter_text.text = filter.title
        }
    }
}


