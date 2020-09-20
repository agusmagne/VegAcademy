package com.vegdev.vegacademy.presenter.recipes.recipes.adapter.single

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.FirebaseStorage
import com.vegdev.vegacademy.contract.RecipesContract
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import kotlinx.android.synthetic.main.recipes_child_single.view.*

class SingleRecipeViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView),
    RecipesContract.View.SingleRecipeView {

    fun onRecipeLikesClick() {
        itemView.isClickable = false
//
//        val recipeId = recipe.id
//        isItLiked = if (!isItLiked) {
//            this.addLikeToView(holder.itemView.likes)
//            holder.colorRecipeAsLiked()
//            onLikeBtnClick(true, recipeId)
//            likedRecipesId?.add(recipeId)
//            true
//        } else {
//            this.substractLikeToView(holder.itemView.likes)
//            holder.colorRecipeAsRegular()
//            onLikeBtnClick(false, recipeId)
//            likedRecipesId?.remove(recipeId)
//            false
//        }
//        it.isClickable = true
    }

    override fun bindRecipe(title: String, likes: String, uri: Uri) {
        itemView.title.text = title
        itemView.likes.text = likes
        Glide.with(itemView.context)
            .load(uri)
            .into(itemView.src)
    }

    override fun bindReturningRecipe(title: String, likes: String, bitmap: Bitmap?, iRecipeView: RecipesContract.View) {
        itemView.title.text = title
        itemView.likes.text = likes
        Glide.with(itemView.context)
            .load(bitmap)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    iRecipeView.startPostponedEnterTransition()
                    return false
                }
            })
            .into(itemView.src)
    }

    override fun checkRecipeAsLiked() {
        TODO("Not yet implemented")
    }

    override fun uncheckRecipeAsLiked() {
        TODO("Not yet implemented")
    }

    override fun getContext(): Context = itemView.context
}