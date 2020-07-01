package com.vegdev.vegacademy.ui.learning

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.category_element.view.*

class LearningRvAdapter(val navController: NavController, val categories: TypedArray, val titles: TypedArray) :
        RecyclerView
.Adapter<LearningViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_element, parent, false)

        return LearningViewHolder(navController, itemView)
    }

    override fun getItemCount(): Int {
        return categories.length()
    }

    override fun onBindViewHolder(holder: LearningViewHolder, position: Int) {

        holder.itemView.element_image_src.background = categories.getDrawable(position)
        holder.itemView.element_title.text = titles.getText(position)

    }

}

class LearningViewHolder(navController: NavController, itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            val parentId = (itemView.parent as View).id
            if(parentId == R.id.videos_rv){
                navController.navigate(R.id.action_video)
            } else if(parentId == R.id.articles_rv){

            }
        }
    }

}