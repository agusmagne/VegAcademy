//package com.vegdev.vegacademy.presenter.recipes.recipes.adapterTObeDEPRECATED
//
//import android.view.View
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.google.firebase.storage.FirebaseStorage
//import com.vegdev.vegacademy.R
//import com.vegdev.vegacademy.model.data.models.SingleRecipe
//import kotlinx.android.synthetic.main.fragment_recipes_element.view.*
//import java.util.*
//
//class RecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    fun bindRecipe(recipe: SingleRecipe) {
//        FirebaseStorage.getInstance().getReference("recipes/recipes/")
//            .child(recipe.id).downloadUrl.addOnSuccessListener {
//                Glide.with(itemView.context).load(it).into(itemView.src)
//            }
//        val title = recipe.title
//        val titleUppercase =
//            title.substring(0, 1).toUpperCase(Locale.ROOT) + title.substring(1, title.length)
//        itemView.title.text = titleUppercase
//        itemView.likes.text = recipe.likes.toString()
//    }
//
//    fun colorRecipeAsLiked() {
//        itemView.likes.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
//        itemView.ic_like.setColorFilter(
//            ContextCompat.getColor(
//                itemView.context,
//                R.color.white
//            )
//        )
//    }
//
//    fun colorRecipeAsRegular() {
//        itemView.likes.setTextColor(
//            ContextCompat.getColor(
//                itemView.context,
//                R.color.superAccent
//            )
//        )
//        itemView.ic_like.setColorFilter(
//            ContextCompat.getColor(
//                itemView.context,
//                R.color.superAccent
//            )
//        )
//    }
//}