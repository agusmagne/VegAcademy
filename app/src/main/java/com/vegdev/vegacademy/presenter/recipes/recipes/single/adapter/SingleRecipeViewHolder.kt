package com.vegdev.vegacademy.presenter.recipes.recipes.single.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.recipes.recipes.SingleRecipeContract
import kotlinx.android.synthetic.main.recipes_child_single.view.*

class SingleRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    SingleRecipeContract.View {

    override fun setUriImageSource(
        uri: Uri,
        onBitmapReady: RequestListener<Drawable?>
    ) {
        Glide.with(itemView.context)
            .load(uri)
            .listener(onBitmapReady)
            .into(itemView.src)
    }

    override fun setBitmapImageSource(
        bitmap: Bitmap?,
        onBitmapReady: RequestListener<Drawable?>
    ) {
        Glide.with(itemView.context)
            .load(bitmap)
            .listener(onBitmapReady)
            .into(itemView.src)
    }

    override fun setRecipeClickable(boolean: Boolean) {
        itemView.src.isClickable = boolean
    }

    override fun isLikeButtonClickable(boolean: Boolean) {
        itemView.likes.isClickable = boolean
    }

    override fun bindText(title: String, likes: String) {
        itemView.title.text = title
        itemView.likes.text = likes
    }

    override fun setLikesButtonColors(isAlreadyLiked: Boolean) {
        if (isAlreadyLiked) {
            val likedDrawable =
                ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_donate, null)
            itemView.likes.setCompoundDrawablesWithIntrinsicBounds(null, null, likedDrawable, null)
            itemView.likes.isSelected = true
            itemView.likes.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
        } else {
            val likedDrawable =
                ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_donate_accent, null)
            itemView.likes.setCompoundDrawablesWithIntrinsicBounds(null, null, likedDrawable, null)
            itemView.likes.isSelected = false
            itemView.likes.setTextColor(ContextCompat.getColor(itemView.context, R.color.navBarOff))
        }

    }

    override fun uncheckRecipeAsLiked() {

    }

    override fun addOrSubtractLikeFromButton(shouldAddLike: Boolean) {
        val likes = if (shouldAddLike) {
            (itemView.likes.text.toString().toInt() + 1).toString()
        } else {
            (itemView.likes.text.toString().toInt() - 1).toString()
        }
        itemView.likes.text = likes
    }

    override fun getContext(): Context = itemView.context
}